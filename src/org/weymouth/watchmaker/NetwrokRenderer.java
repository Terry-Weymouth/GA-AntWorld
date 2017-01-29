package org.weymouth.watchmaker;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.uncommons.watchmaker.framework.interactive.Renderer;
import org.weymouth.ants.AntWorld;
import org.weymouth.ants.Network;

public class NetworkRenderer implements Renderer<Network, JComponent> {
	
	private final NetworkController controller;
	
	private final JComponent itsComponent;

	private XYSeries idealPoints;
	private XYSeries curveFit;
	
	public NetworkRenderer(NetworkController controller) {
		this.controller = controller;
		itsComponent=setup();
	}
	
	@Override
	public JComponent render(Network fn) {
		updateNetworkFit(fn);
		return itsComponent;
	}
	
	private void updateNetworkFit(Network fn) {
		curveFit.clear();
		List<Double> parameters = fn.getParameters();
		TargetNetwork target = controller.getTargetNetwork();
		double x = NetworkFitnessEvaluator.INIT_X;
		double delta = 10.0 * NetworkFitnessEvaluator.DELTA_X;
		int count = NetworkFitnessEvaluator.NUMBER_OF_POINTS/10;
		for (int i = 0; i < count; i++ ){
			double y = target.value(x, parameters);
			curveFit.add(x, y);
			x += delta;
		}
	}

	private void initIdealPoints() {
		idealPoints.clear();
		TargetNetwork target = controller.getTargetNetwork();
		double x = NetworkFitnessEvaluator.INIT_X;
		double delta = 10.0 * NetworkFitnessEvaluator.DELTA_X;
		int count = NetworkFitnessEvaluator.NUMBER_OF_POINTS/10;
		for (int i = 0; i < count; i++ ){
			double y = target.ideal_value(x);
			idealPoints.add(x, y);
			x += delta;
		}
	}

	public JPanel setup() {
		JFreeChart chart = ChartFactory.createXYLineChart(
	            "Fittest Network Fit",       // chart title
	            "X",                      // x axis label
	            "Y",                      // y axis label
	            null,	                  // data
	            PlotOrientation.VERTICAL,  
	            true,                     // include legend
	            true,                     // tooltips
	            false                     // urls
	        );

		XYPlot plot = (XYPlot) chart.getPlot();	
		/* SETUP SCATTER */
	
		// Data collections
		XYDataset collection1 = getScatterPlotData();
		XYDataset collection2 = getLinePlotData();
		initIdealPoints();
		updateNetworkFit(new Network(AntWorld.BRAIN_LAYER_WIDTHS));

		// render collection one as Scatter Plot
		XYItemRenderer renderer1 = new XYLineAndShapeRenderer(false, true);
		ValueAxis domain1 = new NumberAxis("Domain1");
		ValueAxis range1 = new NumberAxis("Range1");
		plot.setDataset(0, collection1);
		plot.setRenderer(0, renderer1);
		plot.setDomainAxis(0, domain1);
		plot.setRangeAxis(0, range1);
		// Map the scatter to the first Domain and first Range
		plot.mapDatasetToDomainAxis(0, 0);
		plot.mapDatasetToRangeAxis(0, 0);
	
		/* SETUP LINE */
	
		// render collection one as Line Plot
		XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false);   // Lines only
		ValueAxis domain2 = new NumberAxis("Domain2");
		ValueAxis range2 = new NumberAxis("Range2");
		plot.setDataset(1, collection2);
		plot.setRenderer(1, renderer2);
		plot.setDomainAxis(1, domain2);
		plot.setRangeAxis(1, range2);
	
		// Map the line to the second Domain and second Range
		plot.mapDatasetToDomainAxis(1, 1);
		plot.mapDatasetToRangeAxis(1, 1);
	
        return new ChartPanel(chart);
	}
	
	private XYDataset getScatterPlotData() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		idealPoints = new XYSeries("Data Points");
		dataset.addSeries(idealPoints);
		return dataset;
	}

	private XYDataset getLinePlotData() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		curveFit = new XYSeries("Curve");
		dataset.addSeries(curveFit);
		return dataset;
	}
}
