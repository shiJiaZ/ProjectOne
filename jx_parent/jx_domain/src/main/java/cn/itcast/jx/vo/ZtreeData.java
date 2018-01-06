package cn.itcast.jx.vo;
/** 
 * 结论：第二个字母除非必须写成大写，否则不要大写
 */
public class ZtreeData {
	private  String id;
	private String pId;
	private String name;
	private String checked;
	private String open;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPId() {
		return pId;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public ZtreeData(String id, String pId, String name, String checked,
			String open) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.checked = checked;
		this.open = open;
	}
	public ZtreeData() {
		super();
	}
	
	
	
	
	
}
