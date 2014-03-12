package dna.graph.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import org.junit.Test;

import dna.profiler.datatypes.benchmarkresults.BenchmarkingResult;
import dna.profiler.datatypes.benchmarkresults.BenchmarkingResultsMap;
import dna.profiler.datatypes.benchmarkresults.strategies.InterpolateMeanValuesStrategy;
import dna.profiler.datatypes.benchmarkresults.strategies.MaxValueUpperBoundaryStrategy;
import dna.profiler.datatypes.benchmarkresults.strategies.MinValueLowerBoundaryStrategy;

public class BenchmarkResultsTest {
	@Test
	public void testParsing() {
		String val = "500=0.7,0.7,0.7;1000=1.3,1.4,1.5";
		BenchmarkingResult res = (BenchmarkingResult) BenchmarkingResult
				.parseString("res", val);

		TreeMap<Integer, ArrayList<Double>> entryMap = new TreeMap<Integer, ArrayList<Double>>();
		BenchmarkingResult expected = new BenchmarkingResult("res", entryMap);
		expected.addToMap(500, Arrays.asList(0.7, 0.7, 0.7));
		expected.addToMap(1000, Arrays.asList(1.3, 1.4, 1.5));
		assertEquals(expected, res);
	}

	@Test
	public void testIfMaxValueUpperBoundaryStrategyWorks() {
		String val = "500=0.7,0.8,0.9;1000=1.6,1.4,1.5";
		BenchmarkingResult.setStrategy(new MaxValueUpperBoundaryStrategy());
		BenchmarkingResult res = (BenchmarkingResult) BenchmarkingResult
				.parseString("", val);		

		res.setValues(1, 40, null);
		BenchmarkingResultsMap results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.9, results.getValue(), 0.1);

		res.setValues(1, 499.9, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.9, results.getValue(), 0.1);	
		
		res.setValues(1, 500.001, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.6, results.getValue(), 0.1);
		
		res.setValues(1, 999.999, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.6, results.getValue(), 0.1);		
	}
	
	@Test
	public void testIfMinValueLowerBoundaryStrategyWorks() {
		String val = "500=0.7,0.8,0.9;1000=1.6,1.4,1.5";
		BenchmarkingResult.setStrategy(new MinValueLowerBoundaryStrategy());
		BenchmarkingResult res = (BenchmarkingResult) BenchmarkingResult
				.parseString("", val);

		res.setValues(1, 40, null);
		BenchmarkingResultsMap results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.7, results.getValue(), 0.1);

		res.setValues(1, 499.9, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.7, results.getValue(), 0.1);		
		
		res.setValues(1, 500, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.7, results.getValue(), 0.1);
		
		res.setValues(1, 999.999, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(0.7, results.getValue(), 0.1);		

		res.setValues(1, 1000, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.4, results.getValue(), 0.1);

		res.setValues(1, 2000, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.4, results.getValue(), 0.1);
	}

	@Test
	public void testCorrectInterpolation() {
		String val = "1=1;2=2";
		BenchmarkingResult.setStrategy(new InterpolateMeanValuesStrategy());
		BenchmarkingResult res = (BenchmarkingResult) BenchmarkingResult
				.parseString("", val);	
		
		res.setValues(1, 1, null);
		BenchmarkingResultsMap results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1, results.getValue(), 0.1);
		
		res.setValues(1, 1.1, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.1, results.getValue(), 0.1);		
		
		res.setValues(1, 1.5, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(1.5, results.getValue(), 0.1);	
		
		res.setValues(1, 2, null);
		results = (BenchmarkingResultsMap) res.getMap();
		assertEquals(2, results.getValue(), 0.1);	
	}
}
