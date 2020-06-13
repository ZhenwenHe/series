package cn.edu.cug.cs.gtl.series.visualization
//https://github.com/JetBrains/lets-plot-kotlin/blob/master/docs/guide/user_guide.ipynb
import cn.edu.cug.cs.gtl.protoswrapper.TimestampWrapper
import cn.edu.cug.cs.gtl.protoswrapper.ValueWrapper
import cn.edu.cug.cs.gtl.series.common.Series
import cn.edu.cug.cs.gtl.series.common.SeriesBuilder
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
import jetbrains.letsPlot.geom.geom_point
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.ggtitle
import jetbrains.letsPlot.intern.Feature
import jetbrains.letsPlot.intern.Options
import jetbrains.letsPlot.intern.Plot
import jetbrains.letsPlot.intern.toSpec
import java.awt.Color
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.sql.Timestamp
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

private fun run( frameTitle:String, p: Plot) {
    SwingUtilities.invokeLater {

        // Create JFXPanel showing the plot.
        val plotSpec = p.toSpec()

        // This plot panel will adapt to dimensions of container
        val component = createPlotPanel(plotSpec) {
            for (message in it) {
                println("PLOT MESSAGE: $message")
            }
        }


        // Show plot in Swing frame.
        val frame = JFrame(frameTitle)
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


class SeriesViewer(private val frameTitle:String, private val figureTitle:String){
    private var data: Any?= null
    private val features:MutableList<Feature> = arrayListOf()

    constructor(frameTitle:String, figureTitle:String, s: Series):this(frameTitle,figureTitle){
        data= mapOf<String, Any>(
                "time" to TimestampWrapper.arrayOf(s.timeValues).asList(),
                "value" to ValueWrapper.arrayOf(s.fieldValues).asList()
        )
        val geom = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.RED) {
            x = "time"; y="value"
        }
        features.add(geom)
    }

    constructor(frameTitle:String, figureTitle:String, s1: Series,s2: Series):this(frameTitle,figureTitle){
        data= mapOf<String, Any>(
                "time" to TimestampWrapper.arrayOf(s1.timeValues).asList(),
                "value" to ValueWrapper.arrayOf(s1.fieldValues).asList(),
                "value2" to ValueWrapper.arrayOf(s2.fieldValues).asList()
        )
        val geom1 = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.RED) {
            x = "time"; y="value"
        }
        features.add(geom1)

        val geom2 = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.GREEN) {
            x = "time"; y="value2"
        }
        features.add(geom2)
    }

    constructor(frameTitle:String, figureTitle:String, s1: Series,s2: Series,s3: Series):this(frameTitle,figureTitle){
        data= mapOf<String, Any>(
                "time" to TimestampWrapper.arrayOf(s1.timeValues).asList(),
                "value" to ValueWrapper.arrayOf(s1.fieldValues).asList(),
                "value2" to ValueWrapper.arrayOf(s2.fieldValues).asList(),
                "value3" to ValueWrapper.arrayOf(s3.fieldValues).asList()
        )
        val geom1 = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.RED) {
            x = "time"; y="value"
        }
        features.add(geom1)

        val geom2 = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.GREEN) {
            x = "time"; y="value2"
        }
        features.add(geom2)

        val geom3 = geom_line(show_legend=true,alpha = 0.8, size = 0.5,color = jetbrains.datalore.base.values.Color.BLUE) {
            x = "time"; y="value3"
        }
        features.add(geom3)
    }

    /**
     * for examples:
     * val geom1 = geom_line(alpha = 0.8, size = 0.5,color = 0.5) {
     * x = "time"; y="value"
     * }
     * val geom2 = geom_line(alpha = 0.8, size = 0.2,color = 5000) {
     * x = "time"; y="value2"
     * }
     * val sv2 = SeriesViewer(frameTitle,figureTitle,data)
     * sv.plot(geom1,geom2);
     */
    fun plot(vararg feature: Feature){
        var p1 = ggplot(data) + ggtitle(figureTitle)
        for (f in features){
            p1+=f
        }
        for (f in feature){
            p1+=f
        }
        val p= p1;
        run(frameTitle,p)
    }

}

