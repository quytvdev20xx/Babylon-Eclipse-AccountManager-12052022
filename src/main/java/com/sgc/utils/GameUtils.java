package com.sgc.utils;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class GameUtils {

	private static GameUtils shared;
	private static SimpleDateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static GameUtils getInstance() {
		if (shared == null) {
			shared = new GameUtils();
		}
		return shared;
	}

//	private GameUtils() {
//		updateExps();
//	}

	private int[] EXP;

	/** get date time */
	public static Timestamp getDateTime() {
		Timestamp datetime = new Timestamp(Calendar.getInstance().getTime().getTime());
		return datetime;
	}

	/** get date time String*/
//	public static String getDateTimeString() {
//		Timestamp datetime = new Timestamp(Calendar.getInstance().getTime().getTime());
//		String time = "";	
//		String timeF = datetime.toString();	
//		String[] arrTime = timeF.split(" ");
//		if (arrTime.length == 2) {
//			time += arrTime[0] + " ";
//			String[] arrTime2 = arrTime[1].split(":");
//			if (arrTime2.length >= 3) {
//				time += arrTime2[0] + ":";
//				time += arrTime2[1] + ":";
//				if (arrTime2[2].length() > 2) {
//					float v1 = Float.parseFloat(arrTime2[2]);
//					int v2 = (int) v1;
//					if (v2 < 10) {
//						time += "0" + v2;
//					} else {
//						time += v2;
//					}
//				} else
//					time += arrTime2[2];
//			} else {
//				time += arrTime[1];
//			}
//		}
//		
//		return time;
//	}
	
	public static String getDateTimeString(String dateTime) {
		String time = "";	
		String[] arrTime = dateTime.split(" ");
		if (arrTime.length == 2) {
			time += arrTime[0] + " ";
			String[] arrTime2 = arrTime[1].split(":");
			if (arrTime2.length >= 3) {
				time += arrTime2[0] + ":";
				time += arrTime2[1] + ":";
				if (arrTime2[2].length() > 2) {
					float v1 = Float.parseFloat(arrTime2[2]);
					int v2 = (int) v1;
					if (v2 < 10) {
						time += "0" + v2;
					} else {
						time += v2;
					}
				} else
					time += arrTime2[2];
			} else {
				time += arrTime[1];
			}
		}
		
		return time;
	}
	
	private void SaveTestTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");		 
		LocalDateTime dateTime1= LocalDateTime.parse("2014-11-25 19:00:00", formatter);
		LocalDateTime dateTime2= LocalDateTime.parse("2014-11-25 16:00:00", formatter);		 
		long diffInMilli = java.time.Duration.between(dateTime1, dateTime2).toMillis();
		long diffInSeconds = java.time.Duration.between(dateTime1, dateTime2).getSeconds();
		long diffInMinutes = java.time.Duration.between(dateTime1, dateTime2).toMinutes();
	}

	public static String[] datetimeFormatter() {
		return datetimeFormatter.format(Calendar.getInstance().getTime()).split(" ");
	}

//	public void updateExps() {
//		ArrayList<String> levelFile = IOFile.readFileLine("files/level.txt");
//		EXP = new int[levelFile.size()];
//		for (int i = 0; i < EXP.length; i++) {
//			String[] st = levelFile.get(i).split(" ");
//			EXP[i] = Integer.parseInt(st[1]);
//		}
//	}

	/** Get level by exp */
	public int getLevel(int exp) {
		int level = 0;
		if (exp >= EXP[EXP.length - 1]) {
			return EXP.length;
		}
		for (int i = 0; i < EXP.length; i++) {
			if (exp == EXP[i]) {
				level = i;
				break;
			} else {
				if (exp > EXP[i]) {
					if (i + 1 < EXP.length) {
						if (exp < EXP[i + 1]) {
							level = i;
							break;
						}
					} else {
						level = i;
						break;
					}
				}
			}
		}
		return level + 1;
	}

	/** get exp by level */
	public float getEXP(int level) {
		if (level > EXP.length) {
			return EXP.length - 1;
		}
		return EXP[level - 1];
	}

//	/**
//	 * Trả về giá trị ngẫu nhiên từ min -> max, ko trùng giá trị valueExist
//	 */
//	public static byte randomValueItem(byte valueExist, int min, int max) {
//		if (min + 1 < max) {
//			EmptyStackException e = new EmptyStackException();
//			e.printStackTrace();
//			throw e;
//		}
//		int value = MathUtils.random(min, max);
//		if (valueExist == value) {
//			while (valueExist == value) {
//				value = MathUtils.random(min, max);
//			}
//		}
//		return (byte) value;
//	}
//
//	/**
//	 * Trả về giá trị ngẫu nhiên từ min -> max, ko trùng giá trị
//	 * valueExist1,valueExist2
//	 */
//	public static byte randomValueItem(byte valueExist1, byte valueExist2, int min, int max) {
//		if (min + 1 < max) {
//			EmptyStackException e = new EmptyStackException();
//			e.printStackTrace();
//			throw e;
//		}
//		int count = 0;
//		int value = MathUtils.random(min, max);
//		if (valueExist1 == value || valueExist2 == value) {
//			while (valueExist1 == value || valueExist2 == value) {
//				value = MathUtils.random(min, max);
//				if (count > 50) {
//					return Byte.MIN_VALUE;
//				}
//			}
//		}
//		return (byte) value;
//	}
//
//	/**
//	 * @return Phần tử ngẫu nhiên của danh sách
//	 */
//	public static byte randomValue(List<Byte> list) {
//		if (list.isEmpty()) {
//			return 0;
//		}
//		int index = MathUtils.random(0, list.size() - 1);
//		return list.get(index);
//	}

	/**
	 * @return Phần tử ngẫu nhiên của danh sách, ko trùng valueExist, =-1 nếu ko tìm
	 *         thấy
	 */
//	public static byte randomValue(List<Byte> list, byte valueExist) {
//		if (list.size() == 1) {
//			if (list.get(0) == valueExist) {
//				return (byte) -1;
//			}
//		}
//		int count = 0;
//		byte value = randomValue(list);
//		if (value == valueExist) {
//			while (value == valueExist) {
//				value = randomValue(list);
//				count++;
//				if (count > 30) {
//					return (byte) -1;
//				}
//			}
//		}
//		return value;
//	}

	/**
	 * Chuyển đổi thời gian sang sosos kim cương tương ứng
	 */
	public static int convertTimeToDiamond(int time) {
		if (time <= 0) {
			return 0;
		}
		int n = time / 600;
		int l = time % 600;
		if (l == 0) {
			return n;
		} else {
			return n + 1;
		}
	}

	/**
	 * Công thức sức chứa trong kho theo level
	 * 
	 * @param d: độ lệch giữa hai level kho
	 */
	public static int getCapacityInWarehouse(int capacity0, int d, int level) {
		return capacity0 + d * level;
	}

	/**
	 * Công thức số gold cần để nâng cấp kho theo level
	 * 
	 * @param d: độ lệch giữa hai level kho
	 */
	public static int getNeedDiamondForUpgradeWarehouse(int diamond0, int d, int level) {
		return diamond0 + d * level;
	}

	private static int[] diamondToOpenQueenmachines = { 1, 2, 6, 14, 30, 62, 126, 254, 510, 1022, 2046, 4094, 8190,
			16382 };
	private static int[] diamondToOpenCarts = { 1, 1, 1, 1, 1, 11, 49, 179, 601, 1931, 6049, 18659 };

	private static int[] needFriendToOpenCarts = { 0, 0, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };

	/**
	 * Số kim cương cần thiết để mở ô sản xuất trên máy tiếp theo if (n < 2) return
	 * 1, ngược lại return Math.pow(2, n) - 2
	 * 
	 * @param n : số thứ tự của hàng đợi
	 * @return số kim cương
	 */
	public static int getDiamondToOpenQueenMachine(int n) {
		if (n < 1) {
			return 1;
		}
		if (n < diamondToOpenQueenmachines.length + 1) {
			return diamondToOpenQueenmachines[n - 1];
		}
		return diamondToOpenQueenmachines[diamondToOpenQueenmachines.length - 1];
	}

	/**
	 * Số kim cương cần thiết để mở ô giỏ hàng thứ n if(n < 5) return 1, ngược lại
	 * return Math.pow(3, (n - 3)) - (int) Math.pow(2, (n - 2))
	 * 
	 * @param n số thứ tự của giỏ hàng
	 * @return số kim cương
	 */
	public static int getDiamondToOpenCart(int n) {
		if (n < 1) {
			return 1;
		}
		if (n < diamondToOpenCarts.length + 1) {
			return diamondToOpenCarts[n - 1];
		}
		return diamondToOpenCarts[diamondToOpenCarts.length - 1];
	}

	public static int getNeedFriendToOpenCart(int n) {
		if (n < 1) {
			return 0;
		}
		if (n < needFriendToOpenCarts.length + 1) {
			return needFriendToOpenCarts[n - 1];
		}
		return needFriendToOpenCarts[needFriendToOpenCarts.length - 1];
	}

	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	/**
	 * push notification với FCM
	 */
//	public static int pushFCMNotification(String deviceToken, String title, String content) {
//		try {
//			if (deviceToken.length() == 0) {
//				return -1;
//			}
//			String authKey = Config.SERVER_KEY_NOTIFICATION;
//			String FMCurl = API_URL_FCM;
//
//			URL url = new URL(FMCurl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//			conn.setUseCaches(false);
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Authorization", "key=" + authKey);
//			conn.setRequestProperty("Content-Type", "application/json");
//
//			JSONObject data = new JSONObject();
//			data.put("to", deviceToken.trim());
//			JSONObject notification = new JSONObject();
//			notification.put("title", title);
//			notification.put("body", content);
//			notification.put("icon", "notification");
//			data.put("notification", notification);
//
//			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//			wr.write(data.toString());
//			wr.flush();
//			wr.close();
//
//			int responseCode = conn.getResponseCode();
//			return responseCode;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		}
//		// BufferedReader in = new BufferedReader(new
//		// InputStreamReader(conn.getInputStream()));
//		// String inputLine;
//		// StringBuffer response = new StringBuffer();
//		//
//		// while ((inputLine = in.readLine()) != null) {
//		// response.append(inputLine);
//		// }
//		// in.close();
//	}

	public static void main(String[] args) {
		for (int i = 1; i <= diamondToOpenQueenmachines.length; i++) {
			// System.out.print("" + getDiamondToOpenQueenMachine(i) + ",");
			System.out.print("" + getDiamondToOpenQueenMachine(i) + ",");
		}
	}

}
