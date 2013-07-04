package dna.series.aggdata;

import java.io.IOException;

import dna.io.filesystem.Files;
<<<<<<< HEAD
import dna.util.Config;
=======
import dna.io.filesystem.Names;
>>>>>>> reworked aggregation
import dna.util.Log;

/**
 * An AggregatedBatch contains aggregated values of a batch.
 * 
 * @author Rwilmes
 * @date 04.07.2013
 */
public class AggregatedBatch {

	// member variables
	private long timestamp;
	private AggregatedValueList stats;
	private AggregatedRunTimeList generalRuntimes;
	private AggregatedRunTimeList metricRuntimes;
	private AggregatedMetricList metrics;
<<<<<<< HEAD

=======
	
>>>>>>> reworked aggregation
	// constructors
	public AggregatedBatch(long timestamp) {
		this.timestamp = timestamp;
		this.stats = new AggregatedValueList();
		this.generalRuntimes = new AggregatedRunTimeList();
		this.metricRuntimes = new AggregatedRunTimeList();
		this.metrics = new AggregatedMetricList();
	}
<<<<<<< HEAD

	public AggregatedBatch(long timestamp, int sizeValues,
			int sizeGeneralRuntimes, int sizeMetricRuntimes, int sizeMetrics) {
=======
	
	public AggregatedBatch(long timestamp, int sizeValues, int sizeGeneralRuntimes,
			int sizeMetricRuntimes, int sizeMetrics) {
>>>>>>> reworked aggregation
		this.timestamp = timestamp;
		this.stats = new AggregatedValueList(sizeValues);
		this.generalRuntimes = new AggregatedRunTimeList(sizeGeneralRuntimes);
		this.metricRuntimes = new AggregatedRunTimeList(sizeMetricRuntimes);
		this.metrics = new AggregatedMetricList(sizeMetrics);
	}

<<<<<<< HEAD
	public AggregatedBatch(long timestamp, AggregatedValueList values,
			AggregatedRunTimeList generalRuntimes,
			AggregatedRunTimeList metricRuntimes, AggregatedMetricList metrics) {
=======
	public AggregatedBatch(long timestamp, AggregatedValueList values, 
			AggregatedRunTimeList generalRuntimes, AggregatedRunTimeList metricRuntimes,
			AggregatedMetricList metrics) {
>>>>>>> reworked aggregation
		this.timestamp = timestamp;
		this.stats = values;
		this.generalRuntimes = generalRuntimes;
		this.metricRuntimes = metricRuntimes;
		this.metrics = metrics;
	}
<<<<<<< HEAD

=======
	
>>>>>>> reworked aggregation
	// methods
	public long getTimestamp() {
		return this.timestamp;
	}
<<<<<<< HEAD

	public AggregatedValueList getValues() {
		return this.stats;
	}

	public AggregatedRunTimeList getGeneralRuntimes() {
		return this.generalRuntimes;
	}

	public AggregatedRunTimeList getMetricRuntimes() {
		return this.metricRuntimes;
	}

	public AggregatedMetricList getMetrics() {
		return this.metrics;
	}

	// IO methods
	public void write(String dir) throws IOException {
		Log.debug("writing AggregatedBatchfor " + this.timestamp + " to " + dir);
		this.stats.write(dir,
				Files.getValuesFilename(Config.get("BATCH_STATS")));
		this.generalRuntimes
				.write(dir, Files.getRuntimesFilename(Config
						.get("BATCH_GENERAL_RUNTIMES")));
		this.metricRuntimes.write(dir,
				Files.getRuntimesFilename(Config.get("BATCH_METRIC_RUNTIMES")));
		this.metrics.write(dir);
	}

	public static AggregatedBatch read(String dir, long timestamp,
			boolean readValues) throws IOException {
		AggregatedValueList values = AggregatedValueList.read(dir,
				Files.getValuesFilename(Config.get("BATCH_STATS")), readValues);
		AggregatedRunTimeList generalRuntimes = AggregatedRunTimeList
				.read(dir, Files.getRuntimesFilename(Config
						.get("BATCH_GENERAL_RUNTIMES")), readValues);
		AggregatedRunTimeList metricRuntimes = AggregatedRunTimeList.read(dir,
				Files.getRuntimesFilename(Config.get("BATCH_METRIC_RUNTIMES")),
				readValues);
		AggregatedMetricList metrics = AggregatedMetricList.read(dir,
				readValues);
		return new AggregatedBatch(timestamp, values, generalRuntimes,
				metricRuntimes, metrics);
	}
=======
	
	public AggregatedValueList getValues() {
		return this.stats;
	}
	
	public AggregatedRunTimeList getGeneralRuntimes() {
		return this.generalRuntimes;
	}
	
	public AggregatedRunTimeList getMetricRuntimes() {
		return this.metricRuntimes;
	}
	
	public AggregatedMetricList getMetrics() {
		return this.metrics;
	}
	
	// IO methods
	public void write(String dir) throws IOException {
		Log.debug("writing AggregatedBatchfor " + this.timestamp + " to " + dir);
		this.stats.write(dir, Files.getValuesFilename(Names.batchStats));
		this.generalRuntimes.write(dir,
				Files.getRuntimesFilename(Names.batchGeneralRuntimes));
		this.metricRuntimes.write(dir,
				Files.getRuntimesFilename(Names.batchMetricRuntimes));
		this.metrics.write(dir);
	}
>>>>>>> reworked aggregation
}
