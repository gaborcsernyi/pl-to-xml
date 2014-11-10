import java.util.Vector;

public class Csomopont2 {
	public String csp_id;
	public String cimke;
	public Vector<String> gyerekei;
	public int melyseg;
	public int terminalis_e;
	public String megjegyzes;
	public String phi;
	public String lemma_ha_term;
	public String alak_ha_term;
	public String alakszam;
	
	public String getCsp_id(){
		return csp_id;
	}
	
	public String getCimke(){
		return cimke;
	}
	
	public Vector<String> getGyerekei(){
		return gyerekei;
	}
	
	public int getTerminalis_e(){
		return terminalis_e;
	}
	
	public String getMegjegyzes(){
		return megjegyzes;
	}
	
	public String getPhi(){
		return phi;
	}

	public String getLemma(){
		return lemma_ha_term;
	}
	
	public String getAlak(){
		return alak_ha_term;
	}
	
	public String getAlakszam(){
		return alakszam;
	}
	
	public Csomopont2 (){
		this.csp_id = "";
		this.cimke = "";
		this.terminalis_e = 0;
		this.megjegyzes = "";
	}
	
	public Csomopont2(String csp_id, String cimke, Vector<String> gyerekei, int terminalis_e, String phi){
		this.csp_id = csp_id;
		this.cimke = cimke;
		this.gyerekei = gyerekei;
		this.terminalis_e = terminalis_e;
		//this.megjegyzes = "";
		this.phi = phi;
	}
	
	public Csomopont2(String csp_id, String cimke, Vector<String> gyerekei, int terminalis_e, String megjegyzes, String phi){
		this.csp_id = csp_id;
		this.cimke = cimke;
		this.gyerekei = gyerekei;
		this.terminalis_e = terminalis_e;
		this.megjegyzes = megjegyzes;
		this.phi = phi;
	}
	
	public Csomopont2(String csp_id, String cimke, int terminalis_e, String megjegyzes){
		this.csp_id = csp_id;
		this.cimke = cimke;
		this.terminalis_e = terminalis_e;
		this.megjegyzes = megjegyzes;
	}
	
	public String toString(){
		String gy = new String();
		for (int x=0; x<gyerekei.size(); x++){
			gy = gy + " " + gyerekei.get(x);
		}
		//gy = gy + "\tsize: " + gyerekei.size();
		return "" + csp_id + "\t" + cimke + "\t\t" + gy + "\talak: "+ alak_ha_term + "\tlemma: " + lemma_ha_term + "\tjegyek: " + megjegyzes + "\t" + terminalis_e;
	}
	
	public String toString2(){
		String gy = new String();
		for (int x=0; x<gyerekei.size(); x++){
			gy = gy + " " + gyerekei.get(x);
		}
		return csp_id + "\t" + cimke + "\t" + gy + "\t" + melyseg;
	}
	
}
