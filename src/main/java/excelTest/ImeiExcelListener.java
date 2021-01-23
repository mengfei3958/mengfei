package excelTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 一键生成所有imei相关的文件
 * @author 0193000683
 *
 */
public class ImeiExcelListener{
	
//	等待三个线程执行完才开始主线程
	private static CountDownLatch countDownLatch = new CountDownLatch(3);
	
	public static void main(String[] args) throws Exception {
//		一定要先检查号段是否已经插入
//		ObtainAllImeiOfFile：根据原始文件获取imei列表和文件名称，生成imei.xlsx，已生成请忽略；
		writeImeiSelect();
		long startTime = System.currentTimeMillis();
		
//		串行，可以指定生成其中某一个文件
//		checkFotaDeploySql();
//		writeInsertFotaSql();
//		writeFotaLogSql();
		
//		并行
		imeiListener();
		countDownLatch.await();
		long endTime = System.currentTimeMillis();
		System.out.println("spend time = " + (endTime - startTime));
	}
	
	/**
	 * 根据imei.xlsx文件提取出号段列表，包括连续的和所有不连续的imei号，生成SD2500_imei.xlsx
	 * @throws Exception
	 */
	private static void writeImeiSelect() throws Exception{
		WriteImeiSelect writeImeiSelect = new WriteImeiSelect();
		writeImeiSelect.excelReadOfSegmentation();
	}
	
	/**
	 * CheckFotaDeploySql（针对起始号段和结束号段）：根据SD2500_imei.xlsx号段区间检查已部署的号段管理、差分包关联、强制升级规则的sql插入结果的正确性，
	 * 生成checkWriteInsertFotaSql.xlsx；
	 */
	private static void checkFotaDeploySql() throws Exception{
		CheckFotaDeploySql checkFotaDeploySql = new CheckFotaDeploySql();
		checkFotaDeploySql.excelRead();
	}
	
	/**
	 * InsertFotaSql：根据SD2500_imei.xlsx号段区间,把提取出的imei号段批量生成号段管理、差分包关联、强制升级规则的sql插入语句，
	 * 生成writeInsertFotaSql.xlsx；
	 * @throws Exception 
	 */	
	private static void writeInsertFotaSql() throws Exception{
		WriteInsertFotaSql writeInsertFotaSql = new WriteInsertFotaSql();
		writeInsertFotaSql.excelRead();
	}
	
	/**
	 * 根据SD2500_imei.xlsx号段区间生成每日工作日报的sql查询语句，生成writeFotaLogSql.xlsx
	 * @throws Exception
	 */
	private static void writeFotaLogSql() throws Exception{
		WriteFotaLogSql writeFotaLogSql = new WriteFotaLogSql();
		writeFotaLogSql.excelRead();
	}
	
	private static Executor threadPoolTaskExecutor(){
		Executor threadPool = Executors.newFixedThreadPool(5);
		return threadPool;
	}
	
	/**
	 * 多线程生成imei相关文件
	 */
	private static void imeiListener(){
		Executor threadPool = threadPoolTaskExecutor();
        threadPool.execute(() -> {
        	try {
				checkFotaDeploySql();
				countDownLatch.countDown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        threadPool.execute(() -> {
        	try {
        		writeInsertFotaSql();
        		countDownLatch.countDown();
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        });
        threadPool.execute(() -> {
        	try {
        		writeFotaLogSql();
        		countDownLatch.countDown();
        	} catch (Exception e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        });
	}
}
