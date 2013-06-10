package dna.metrics.clusterCoefficient;

import dna.graph.directed.DirectedEdge;
import dna.graph.directed.DirectedGraph;
import dna.graph.directed.DirectedNode;
import dna.updates.Batch;
import dna.updates.EdgeAddition;
import dna.updates.EdgeRemoval;
import dna.updates.NodeAddition;
import dna.updates.NodeRemoval;
import dna.updates.Update;
import dna.util.ArrayUtils;

@SuppressWarnings("rawtypes")
public class OpenTriangleClusteringCoefficientUpdate extends
		OpenTriangleClusteringCoefficient {

	public OpenTriangleClusteringCoefficientUpdate() {
		super("openTriangleClusteringCoefficientUpdate",
				ApplicationType.BeforeAndAfterUpdate);
	}

	@Override
	public boolean applyBeforeBatch(Batch b) {
		return false;
	}

	@Override
	public boolean applyAfterBatch(Batch b) {
		return false;
	}

	@Override
	public boolean applyBeforeUpdate(Update u) {
		if (u instanceof EdgeRemoval) {
			DirectedEdge e = (DirectedEdge) ((EdgeRemoval) u).getEdge();
			DirectedNode a = e.getSrc();
			DirectedNode b = e.getDst();

			// t1
			for (DirectedNode c : a.getNeighbors()) {
				if (b.hasNeighbor(c)) {
					this.removeTriangle(c);
				}
			}
			
			// t2 / t3
			if(a.hasNeighbor(b)){
				// t2
				for (DirectedNode c : a.getNeighbors()) {
					if (!a.hasNeighbor(b)) {
						continue;
					}
					if (c.hasEdge(new DirectedEdge(c, b))) {
						this.removeTriangle(a);
					}
					if (c.hasEdge(new DirectedEdge(b, c))) {
						this.removeTriangle(a);
					}
				}
				
				// t3
				for (DirectedNode c : b.getNeighbors()) {
					if (c.hasEdge(new DirectedEdge(c, a))) {
						this.removeTriangle(b);
					}
					if (c.hasEdge(new DirectedEdge(a, c))) {
						this.removeTriangle(b);
					}
				}
				
			}

			// p
			if (a.hasNeighbor(b)) {
				this.removePotentials(a, 2 * (a.getNeighborCount() - 1));
				this.removePotentials(b, 2 * (b.getNeighborCount() - 1));
			}
		}
		return true;
	}

	@Override
	public boolean applyAfterUpdate(Update u) {
		if (u instanceof NodeAddition) {
			// TODO implement node addition @ openTriangle CC update
		} else if (u instanceof NodeRemoval) {
			// TODO implement node removal @ openTriangle CC update
		} else if (u instanceof EdgeAddition) {
			DirectedEdge e = (DirectedEdge) ((EdgeAddition) u).getEdge();
			DirectedNode a = e.getSrc();
			DirectedNode b = e.getDst();

			// t1
			for (DirectedNode c : a.getNeighbors()) {
				if (b.hasNeighbor(c)) {
					this.addTriangle(c);
				}
			}

			// t2 / t3
			if (a.hasNeighbor(b)) {
				
				// t2
				for (DirectedNode c : a.getNeighbors()) {
					if (!a.hasNeighbor(b)) {
						continue;
					}
					if (c.hasEdge(new DirectedEdge(c, b))) {
						this.addTriangle(a);
					}
					if (c.hasEdge(new DirectedEdge(b, c))) {
						this.addTriangle(a);
					}
				}
				
				// t3
				for (DirectedNode c : b.getNeighbors()) {
					if (c.hasEdge(new DirectedEdge(c, a))) {
						this.addTriangle(b);
					}
					if (c.hasEdge(new DirectedEdge(a, c))) {
						this.addTriangle(b);
					}
				}
				
			}

			// p
			if (a.hasNeighbor(b)) {
				this.addPotentials(a, 2 * (a.getNeighborCount() - 1));
				this.addPotentials(b, 2 * (b.getNeighborCount() - 1));
			}

		}
		return true;
	}

	@Override
	public boolean compute() {
		this.triangleCount = 0;
		this.potentialCount = 0;
		DirectedGraph g = (DirectedGraph) this.g;
		for (DirectedNode n : g.getNodes()) {
			this.nodeTriangleCount[n.getIndex()] = 0;
			this.nodePotentialCount[n.getIndex()] = 0;
			for (DirectedNode u : n.getNeighbors()) {
				for (DirectedNode v : n.getNeighbors()) {
					if (u.equals(v)) {
						continue;
					}
					this.nodePotentialCount[n.getIndex()]++;
					if (u.hasEdge(new DirectedEdge(u, v))) {
						this.nodeTriangleCount[n.getIndex()]++;
					}
				}
			}
			this.triangleCount += this.nodeTriangleCount[n.getIndex()];
			this.potentialCount += this.nodePotentialCount[n.getIndex()];
			if (this.nodePotentialCount[n.getIndex()] == 0) {
				this.localCC[n.getIndex()] = 0;
			} else {
				this.localCC[n.getIndex()] = (double) this.nodeTriangleCount[n
						.getIndex()]
						/ (double) this.nodePotentialCount[n.getIndex()];
			}
		}

		if (this.potentialCount == 0) {
			this.globalCC = 0;
		} else {
			this.globalCC = (double) this.triangleCount
					/ (double) this.potentialCount;
		}
		this.averageCC = ArrayUtils.avg(this.localCC);

		return true;
	}

}