package dna.metrics.betweenessCentrality;

import dna.diff.Diff;
import dna.diff.DiffNotApplicableException;
import dna.graph.Edge;

public class BCComp extends BetweenessCentrality {

	public BCComp() {
		super("BCComp", false, false, false);
	}

	protected boolean compute_() {
		super.reset_();
		return super.compute_();
	}

	@Override
	protected boolean applyBeforeDiff_(Diff d)
			throws DiffNotApplicableException {
		throw new DiffNotApplicableException("before diff");
	}

	@Override
	protected boolean applyAfterEdgeAddition_(Diff d, Edge e)
			throws DiffNotApplicableException {
		throw new DiffNotApplicableException("after edge addition");
	}

	@Override
	protected boolean applyAfterEdgeRemoval_(Diff d, Edge e)
			throws DiffNotApplicableException {
		throw new DiffNotApplicableException("after edge removal");
	}

	@Override
	protected boolean applyAfterDiff_(Diff d) throws DiffNotApplicableException {
		throw new DiffNotApplicableException("after diff");
	}

}
