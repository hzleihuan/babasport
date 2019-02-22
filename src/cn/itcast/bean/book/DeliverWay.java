package cn.itcast.bean.book;
/**
 * 配送方式
 */
public enum DeliverWay {
	GENERALPOST{
		public String getName(){
			return "平邮";
		}
	},
	EXPRESSDELIVERY{
		public String getName(){
			return "快递送货上门";
		}
	},
	EXIGENCEEXPRESSDELIVERY{
		public String getName(){
			return "加急快递送货上门";
		}
	},
	EMS{
		public String getName(){
			return "国内特快专递EMS";
		}
	};
	public abstract String getName();
}
