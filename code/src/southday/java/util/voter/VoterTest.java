package southday.java.util.voter;

public class VoterTest {
	public static void main(String[] args) throws Exception {
		Voter voter1 = new Voter();
		String ipsFilePath1 = "E:/ip1.txt";
		voter1.readIps(ipsFilePath1);
		
		// 子线程投票1
		VoterThread voterThread = new VoterThread("E:/ip2.txt");
		voterThread.setName("Thread1");
		voterThread.start();
		
		// 子线程投票2
		VoterThread voterThread2 = new VoterThread("E:/ip3.txt");
		voterThread2.setName("Thread2");
		voterThread2.start();
		
		// 父线程投票
		voter1.vote();
		
		int totalVoteNum = voter1.getSuccessNum() + voter1.getFailureNum() 
						  + voterThread.getFailureNum() + voterThread.getSuccessNum()
						  + voterThread2.getFailureNum() + voterThread2.getSuccessNum();
		int totalSuccessNum = voterThread.getSuccessNum() + voterThread2.getSuccessNum() + voter1.getSuccessNum();
		int totalFailureNum = voterThread.getFailureNum() + voterThread2.getFailureNum() + voter1.getFailureNum();
		System.out.println("总投票数: " + totalVoteNum + ", 总成功数: " + totalSuccessNum + ", 总失败数: " + totalFailureNum);
	}
}
