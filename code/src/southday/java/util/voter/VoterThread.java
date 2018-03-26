package southday.java.util.voter;

public class VoterThread extends Thread {
	private Voter voter = null;
	
	public VoterThread(Voter voter) {
		this.voter = voter;
	}
	
	public VoterThread(String ipsFilePath) {
		this.voter = new Voter();
		try {
			this.voter.readIps(ipsFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		voter.vote();
	}
	
	public int getSuccessNum() {
		return voter.getSuccessNum();
	}
	
	public int getFailureNum() {
		return voter.getFailureNum();
	}
}
