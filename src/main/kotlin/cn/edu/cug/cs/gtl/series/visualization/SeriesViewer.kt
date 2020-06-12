package cn.edu.cug.cs.gtl.series.visualization

import javafx.application.Platform
import jetbrains.datalore.base.geometry.DoubleVector
import jetbrains.datalore.base.observable.property.ValueProperty
import jetbrains.datalore.plot.MonolithicAwt
import jetbrains.datalore.plot.builder.PlotContainer
import jetbrains.datalore.plot.builder.presentation.Style
import jetbrains.datalore.plot.config.PlotConfig
import jetbrains.datalore.plot.config.PlotConfigClientSide
import jetbrains.datalore.plot.config.PlotConfigClientSideUtil
import jetbrains.datalore.plot.config.PlotConfigUtil
import jetbrains.datalore.plot.server.config.PlotConfigServerSide
import jetbrains.datalore.vis.svg.SvgSvgElement
import jetbrains.datalore.vis.swing.SceneMapperJfxPanel
import jetbrains.letsPlot.geom.geom_histogram
import jetbrains.letsPlot.geom.geom_boxplot
import jetbrains.letsPlot.geom.geom_line
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.ggtitle
import jetbrains.letsPlot.intern.toSpec
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.swing.*
import javax.swing.border.LineBorder

// Setup
private val SVG_COMPONENT_FACTORY_JFX =
        { svg: SvgSvgElement -> SceneMapperJfxPanel(svg, listOf(Style.JFX_PLOT_STYLESHEET)) }

private val JFX_EDT_EXECUTOR = { runnable: () -> Unit ->
    if (Platform.isFxApplicationThread()) {
        runnable.invoke()
    } else {
        Platform.runLater(runnable)
    }
}

private const val PADDING = 20

private fun toPlotSize(containerSize: Dimension) = DoubleVector(
        containerSize.width.toDouble() - 2 * PADDING,
        containerSize.height.toDouble() - 2 * PADDING
)

fun main() {
    SwingUtilities.invokeLater {

        // Generate random data-points
        val rand = Random()
        val data = mapOf<String, Any>(
                "time" to List(100) { i->i },
                "value" to List(50) { rand.nextGaussian() } + List(50) { rand.nextGaussian() + 1.0 }
                )

        // Create plot specs using Lets-Plot Kotlin API
//        val geom = geom_histogram(alpha = 0.8, size = 0.0) {
//            x = "x"; fill = "c"
//        }
        val geom = geom_line(alpha = 0.8, size = 0.2,color = 0.5) {
            x = "time"; y="value"
        }
        val p = ggplot(data) + geom + ggtitle("The normal distribution")

        // Create JFXPanel showing the plot.
        val plotSpec = p.toSpec()

        // This plot panel will adapt to dimensions of container
        val component = createPlotPanel(plotSpec) {
            for (message in it) {
                println("PLOT MESSAGE: $message")
            }
        }


        // Show plot in Swing frame.
        val frame = JFrame("The Minimal Resizable")
        val contentPane = frame.contentPane
        contentPane.add(component)
        frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        frame.size = Dimension(700, 400)
        frame.isVisible = true
    }
}

private fun createPlotPanel(
        plotSpec: MutableMap<String, Any>,
        computationMessagesHandler: ((List<String>) -> Unit)
): JPanel {
    // TODO: add high level api

    // apply all transforms
    @Suppress("NAME_SHADOWING")
    var plotSpec = PlotConfigServerSide.processTransform(plotSpec)
    plotSpec = PlotConfigClientSide.processTransform(plotSpec)
    if (PlotConfig.isFailure(plotSpec)) {
        val errorMessage = PlotConfig.getErrorMessage(plotSpec)
        throw RuntimeException(errorMessage)
    }

    val computationMessages = PlotConfigUtil.findComputationMessages(plotSpec)
    if (computationMessages.isNotEmpty()) {
        computationMessagesHandler(computationMessages)
    }

    // Create panel-container for plot component and start listening its `resized` events.
    val panel = JPanel()
    panel.border = LineBorder(Color.LIGHT_GRAY, PADDING)
    panel.layout = FlowLayout(FlowLayout.CENTER, 0, 0)

    panel.addComponentListener(object : ComponentAdapter() {
        private val eventCount: AtomicInteger = AtomicInteger(0)
        private var plotCreated = false
        private val plotSizeProp = ValueProperty(DoubleVector.ZERO)
        override fun componentResized(e: ComponentEvent) {
            eventCount.incrementAndGet()

            val executor: (() -> Unit) -> Unit = if (plotCreated) {
                // Supposedly, Java FX has already been initialized at this time
                JFX_EDT_EXECUTOR
            } else {
                // Java FX is not yet started - execute in Swing EDT
                { runnable: () -> Unit -> SwingUtilities.invokeLater(runnable) }
            }

            executor {
                if (eventCount.decrementAndGet() == 0) {
                    val container = e.component as JComponent
                    container.invalidate()

                    val newPlotSize = toPlotSize(e.component.size)

                    // existing plot will be updated here
                    plotSizeProp.set(newPlotSize)

                    if (!plotCreated) {
                        plotCreated = true

                        // create plot
                        val assembler = PlotConfigClientSideUtil.createPlotAssembler(plotSpec)
                        val plot = assembler.createPlot()
                        val plotContainer = PlotContainer(plot, plotSizeProp)

                        val component = MonolithicAwt.buildPlotComponent(
                                plotContainer,
                                SVG_COMPONENT_FACTORY_JFX,
                                JFX_EDT_EXECUTOR
                        )
                        component.border = BorderFactory.createLineBorder(Color.BLUE, 1)
                        container.add(component)
                    }

                    container.revalidate()
                }
            }
        }
    })
    return panel
}


class SeriesViewer{

}