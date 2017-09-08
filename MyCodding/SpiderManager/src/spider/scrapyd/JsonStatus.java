package spider.scrapyd;

import java.util.List;

public class JsonStatus {
	String status;
	List<PendingStatus> pending;
	List<RunningStatus> running;
	List<FinishedStatus> finished;
	String node_name;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<PendingStatus> getPending() {
		return pending;
	}

	public void setPending(List<PendingStatus> pending) {
		this.pending = pending;
	}

	public List<RunningStatus> getRunning() {
		return running;
	}

	public void setRunning(List<RunningStatus> running) {
		this.running = running;
	}

	public List<FinishedStatus> getFinished() {
		return finished;
	}

	public void setFinished(List<FinishedStatus> finished) {
		this.finished = finished;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

}
