
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	//	String url = "www.baidu.com/s?wd=url%20regex%20http&rsv_spt=1&rsv_iqid=0xa2f9b24e00001f87&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&oq=url%2520regex&rsv_t=897c57yvUIJ%2F9nG%2B59ef7F%2BlVTNal9z3uzZjTR07oIuvwK9S5EH8E5ZqRSjsbxBCC9I4&inputT=1131&rsv_sug3=17&rsv_sug1=7&rsv_pq=dec2b53a000000ed&rsv_sug2=0&rsv_sug4=1334&rsv_sug=1";
	//	System.out.println(url.matches("^[http:\\/\\/,https:\\/\\/]*[a-z,A-Z,0-9]+\\.[a-z,A-Z,0-9]+\\.[a-z,A-Z,0-9]+.+$"));
	String tags = "东丽湖万科 城洋房天津 东丽湖万科 东丽湖万科城洋";
	System.out.println(tags.matches("^(\\S{1,20}\\s{1,3}){0,8}\\S{1,20}\\s{0,3}$"));
	}

}
