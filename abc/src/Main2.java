

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.eko.reqpay.ReqPay;

public class Main2 {
	private static volatile AtomicInteger amount = new AtomicInteger(100);
	private static volatile AtomicInteger totalamount = new AtomicInteger(0);
	
	/**
	 * @param args
	 * @throws InterruptedException
	 */
	// public static void main(String[] args) throws InterruptedException {
	// // TODO Auto-generated method stub
	// long s = System.currentTimeMillis();
	// for (int i = 0; i < 20; i++) {
	// System.out.println("Starttime" + s);
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// postRequest(amount.addAndGet(5));
	// totalamount.addAndGet(amount.get());
	// }
	// }).start();
	// if (i % 5 == 0) {
	// Thread.sleep(1000l);
	// }
	// }
	// long e = System.currentTimeMillis();
	// System.out.println("time taken " + (e - s) + "");
	// System.out.println("total amount " + totalamount.get());
	//
	// // postRequest(interaction_type_id, bookno, initiator_id, amount);
	// }
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ReqPay upi = new ReqPay();
		upi.setHead(new ReqPay.Head());
		upi.getHead().setMsgId("1");
		upi.getHead().setOrgId("1");
		upi.getHead().setTs("12000-233-23");
		upi.getHead().setVer(new BigDecimal(1.1));
		try {
			
			File file = new File("/home/sauku01/file.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(ReqPay.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// jaxbMarshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper",
			// new Mapper());
			
			jaxbMarshaller.marshal(upi, file);
			jaxbMarshaller.marshal(upi, System.out);
			
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		// postRequest(interaction_type_id, bookno, initiator_id, amount);
	}
	
	public static String[] postRequest(Integer amount) {
		String status = null;
		String oldbook = null;
		String uid = null;
		HttpClient httpclient = new HttpClient();
		BufferedReader bufferedreader = null;
		// "=10002340&=110&timestamp=1990-01-01T01:01:01Z&currency=INR&=9901473431&=9910028267&=1234&=100&=1&=2"
		// http://localhost:8080/eko/v1/transactions/
		// curl -i -X POST -d
		// "recipient_id=10002340&amount=110&timestamp=1990-01-01T01:01:01Z&currency=INR&customer_id=9901473431
		// &initiator_id=9910028267&client_ref_id=1234&hold_timeout=100&state=1&=2"
		// http://localhost:8080/eko/v1/transactions/
		//
		String url = "https://staging.eko.co.in:25004/ekoapi/v1/transactions";
		System.out.println(url);
		System.out.println(amount);
		
		PostMethod postmethod = new PostMethod(url);
		Header header = new Header();
		header.setName("developer_key");
		header.setValue("becbbce45f79c6f5109f848acd540567");
		postmethod.addRequestHeader(header);
		postmethod.addParameter("recipient_id", 10006457 + "");
		postmethod.addParameter("amount", amount + "");
		postmethod.addParameter("timestamp", "1990-01-01T01:01:01Z");
		postmethod.addParameter("currency", "INR");
		postmethod.addParameter("customer_id", "6565656561");
		postmethod.addParameter("initiator_id", "9811938157");
		postmethod.addParameter("client_ref_id", "145644444");
		postmethod.addParameter("hold_timeout", "100");
		postmethod.addParameter("state", "1");
		postmethod.addParameter("channel", "2");
		
		
		try {
			int rCode = httpclient.executeMethod(postmethod);
			System.out.println("rCode is" + rCode);
			
			if (rCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				System.err.println("The Post postmethod is not implemented by this URI");
				postmethod.getResponseBodyAsString();
			} else if (rCode == HttpStatus.SC_NOT_ACCEPTABLE) {
				System.out.println(postmethod.getResponseBodyAsString());
			} else {
				bufferedreader = new BufferedReader(new InputStreamReader(postmethod.getResponseBodyAsStream()));
				String readLine;
				while ((readLine = bufferedreader.readLine()) != null) {
					System.out.println("return value " + readLine);
					try {
						JSONParser parser = new JSONParser();
						status = String.valueOf(((JSONObject) parser.parse(readLine)).get("status"));
						JSONObject data = (JSONObject) ((JSONObject) parser.parse(readLine)).get("data");
						if (null != data) {
							oldbook = String.valueOf(data.get("booklet_serial_number"));
							uid = String.valueOf(data.get("state"));
						}
					} catch (ParseException pe) {
						pe.printStackTrace();
					}
				}
				
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			postmethod.releaseConnection();
			if (bufferedreader != null) {
				try {
					bufferedreader.close();
				} catch (Exception fe) {
					fe.printStackTrace();
				}
			}
		}
		String[] resarr = new String[3];
		resarr[0] = status;
		resarr[1] = oldbook;
		resarr[2] = uid;
		return resarr;
	}
}
