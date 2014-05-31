package graph;

import java.math.BigDecimal;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Graph {
	public String id;
	public ChartPanel chartpanel;
	public JFreeChart jfreechart;
	public XYSeries xyseries;

	// public HashMap<Integer, BigDecimal> errorProgressMap=new HashMap<Integer,
	// BigDecimal>();
	public Graph(String _id) {
		id = _id;
	}

	public void put(int iteration, BigDecimal value) {
		xyseries.add(iteration, value);
		chartpanel.repaint();
	}

	public JPanel createPanel() {
		JFreeChart jfreechart = createChart(createDataset());
		chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}

	private XYDataset createDataset() {
		xyseries = new XYSeries("Error set");
		// xyseries.add(1.0D, 1.0D);
		// xyseries.add(2D, 4D);
		// xyseries.add(3D, 3D);
		// xyseries.add(4D, 5D);
		// xyseries.add(5D, 5D);
		// xyseries.add(6D, 7D);
		// xyseries.add(7D, 7D);
		// xyseries.add(8D, 8D);
		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(xyseries);
		return xyseriescollection;
	}

	private JFreeChart createChart(XYDataset xydataset) {
		jfreechart = ChartFactory.createXYLineChart("Error Progress",
				"Iteration", "Errors", xydataset, PlotOrientation.VERTICAL,
				true, true, false);
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(true);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot
				.getRenderer();
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setBaseShapesFilled(true);
		NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return jfreechart;
	}

}
