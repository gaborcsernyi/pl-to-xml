import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Collections;

public class Code_parse {

	public static String kinyer(String s, String kezd, String veg){
		 int kezd_poz = s.indexOf(kezd);
		 int veg_poz = s.indexOf(veg, kezd_poz);
		 kezd_poz = kezd_poz  + kezd.length();
		 String str = new String();
		 str = s.substring(kezd_poz, veg_poz);
		 return str;
	 }
	 public static String kinyer_kezdpoz(String s, int kezd, String veg) {
		 int veg_poz = s.indexOf(veg, kezd);
		 String str = new String();
		 str = s.substring(kezd, veg_poz);
		 return str;
	 }
	
	 public static void main(String[] args) {
		 // az elemzeseket tartalmazo fajl beolvasasa
		 File inputdir = new File(args[0]);
		 //System.out.println("### " + inputdir + " ###");
		 File[] files = inputdir.listFiles();
		 			
			
		 try{
/*			 FileInputStream fis = new FileInputStream(""); 
		     BufferedReader beelemz = new BufferedReader (new InputStreamReader (fis,"UTF-8"));
		     FileOutputStream fos1 = new FileOutputStream("");
		     OutputStreamWriter kimenet = new OutputStreamWriter(fos1,"UTF-8"); */
			 BufferedWriter kimenet = new BufferedWriter(new FileWriter(args[1]));
			 int mondatszamol = 0;
for (int fctr=0; fctr<files.length; fctr++){
	String into_file = new String();
	String current_file = files[fctr].getAbsolutePath();
	if (current_file.endsWith(".pl")){
		mondatszamol++;
		System.out.println("[" + mondatszamol + "]\t### " + current_file + " ###");
		      BufferedReader beelemz = new BufferedReader(new FileReader(current_file));
		      
		      String s;
		      String elemzes;
		      String c_str;
		      //int mondatszamol = 0;
		      
		      //int mondat_kezd = elemz.indexOf("fstructure('");
		      //int mondat_veg = elemz.indexOf("',", mondat_kezd);
		      //String mondat = elemz.substring(mondat_kezd+12, mondat_veg);
		      //System.out.println(mondat);
		      
		      elemzes="";
		      // a fajl tartalmanak beolvasasa valtozoba
		      while ( (s = beelemz.readLine()) != null ) {
		    	  elemzes = elemzes + s.trim() + '\n';
		    	  //System.out.println(s);
		      }
		      
		      //int mondat_kezd = elemz.indexOf("fstructure('");
		      //int mondat_veg = elemz.indexOf("',", mondat_kezd);
		      //String mondat = elemz.substring(mondat_kezd+12, mondat_veg);
		      //System.out.println(mondat);
			
		      int mondat_kezd = elemzes.indexOf("fstructure('");
		      int mondat_veg = elemzes.indexOf("',", mondat_kezd+12);
		      String mondat = elemzes.substring(mondat_kezd+12, mondat_veg);
		      //System.out.println(mondat);
		      
		      // a root kategoria kinyerese
		      int rootcat_kezd = elemzes.indexOf("'rootcategory'('");
		      int rootcat_veg = elemzes.indexOf("'),", rootcat_kezd);
		      String rootcat = elemzes.substring(rootcat_kezd+16, rootcat_veg);
		      //System.out.println(rootcat);
		      
		      // a megoldasok szamanak kinyerese
		      int solutions_kezd = elemzes.indexOf("'statistics'('");
		      int solutions_veg = elemzes.indexOf("solutions,", solutions_kezd);
		      String solutions = elemzes.substring(solutions_kezd+14, solutions_veg).trim();
		      if (solutions.indexOf("+")>0) solutions=solutions.substring(0 , solutions.indexOf("+"));
		      int solution = Integer.valueOf(solutions);
		      //System.out.println("\n Number of analyses: " + solution + "\n");
		      
		      
		      // a choices resz kinyerese
		      String choices = new String();
		      int choices_fej = elemzes.indexOf("% Choices:");
		      int choices_kezd = elemzes.indexOf("[", choices_fej);
		      int choices_veg = elemzes.indexOf("\n],\n", choices_fej);
		      choices = elemzes.substring(choices_kezd+2, choices_veg);
		      //if (choices.isEmpty()) System.out.println("baa");
		      
		      // a choices sorainak feldolgozasa
		      String[] chs = choices.split("\n");
		      
		      Vector<String> opciok = new Vector<String>();
	    	  // az egyes szamu opcio mindig kell, akkor is, ha ures a choices resz -- egy elemzes mindig van
	    	  opciok.add("1");
	    	  if (!choices.isEmpty()){	    	  
			      // letarolas kulon tombelemenkent
			      for (int x=0; x<chs.length; x++){
			    	  // csak a zarojelezett resz megtartasa -- "choice" szoveg es sor vegi irasjel eltavolitasa
			    	  chs[x] = chs[x].substring(7, chs[x].lastIndexOf(')'));
			    	  // az elso, szogeletes zarojellel ellatott resz kinyerese
			    	  String[] darabolt1 = kinyer(chs[x], "[", "]").split(",");
			    	  // a masodik resz kinyerese -- itt vannak az "or" operatoros kifejezesek
			    	  String masodik_resz = chs[x].substring(chs[x].indexOf("],")+3);
			    	  // az OR-reszek surithetok (?)
			    	  if (masodik_resz.contains("or(")==true){
			    		  masodik_resz = masodik_resz.replace("or(", "");
			    		  masodik_resz = masodik_resz.replace(")", "");
			    		  //System.out.println(masodik_resz);
			    	  }
			    	  
			    	  String[] darabolt2 = masodik_resz.split(",");
			    	  
			    	  //System.out.print(darabolt1[y] + "\t");
		    		  for (int z=0; z<darabolt2.length; z++){
		    			  //System.out.println(darabolt2[z] + " =>  ");
		    			  Vector<Integer> i = new Vector<Integer>();
		    			  // megnezzuk, hogy mely eddig feldolgozott elemzesi utvonalon szerepelt mar a jobb oldali choice elem
		    			  for (int v=0; v<opciok.size(); v++){
		    				  //System.out.println("!!! " + opciok.get(v));
		    				  String[] opc_darab = opciok.get(v).split(",");
		    				  for (int drb_count=0; drb_count < opc_darab.length; drb_count++)
		    				   if (opc_darab[drb_count].equals(darabolt2[z])){
		    					  i.add(v);
		    					  //System.out.println("yep!");
		    				  }
		    			  }
	
		    			  // amelyik vektorelem tartalmazza az eddig elemzesi utvonalat a jobb oldali choices elemmel, 
		    			  // torlesre jeloljuk ("-" kerul bele),  majd ujra felvesszuk, kibovitve a bal oldali choice elemmel
		    			  for (int w=0; w<i.size();w++){
							  String temp = opciok.get(i.get(w));
						  	  opciok.set(i.get(w), "---");
						  	  //System.out.println("marked for removal!");
							  for (int y=0; y<darabolt1.length; y++){
							  	  opciok.add(temp + "," + darabolt1[y]);
							  	  //System.out.println(temp + "," + darabolt1[y]);
		    				  }						  
		    			  }
		    		  }	  
			      }
	    	  }		     
	    	  
		      // A LEHETSÉGES ELEMZESI LEHETŐSÉGEK
	    	  //System.out.println("CHOICES:");
	    	  for (int c=0; c<opciok.size(); c++){
		    	  if (opciok.get(c).contentEquals("---")){
		    		  //System.out.println("tÄ‚Â¶rÄ‚Â¶l: " +opciok.get(c)); 
		    		  opciok.remove(c);
		    		  c--;
		    	  }
		    	  // A LEHETSÉGES ELEMZESI LEHETŐSÉGEK KIÍRÁSA
		    	  //else System.out.println("\t" + opciok.get(c));  
		      }
		      
		      
	    	  //System.out.println("\nA VEKTOR MÉRETE: " + opciok.size() + "\n");
		      
		      if (opciok.size() != solution){
		    	  System.out.println(opciok.size() + "\t" + solution);
		    	  System.err.println("PROBLEM: the number of solutions does not equal to the number of solutions given in the CHOICES section.");
		      }
		      
	    	  // az Equivalences resz feldolgozasa
	    	  String eqs_teljes = new String();
		      int eqs_fej = elemzes.indexOf("% Equivalences");
		      int eqs_kezd = elemzes.indexOf("[", eqs_fej);
		      int eqs_veg = elemzes.indexOf("\n],\n", eqs_fej);
		      //System.out.println(eqs_fej + "\t" + eqs_kezd + "\t" + eqs_veg);
		      eqs_teljes = elemzes.substring(eqs_kezd+2, eqs_veg);
		      //System.out.println(eqs_teljes);
	    	  
		      // az Equivalences resz mint egy vektor (eqs_fin) elemei lesznek letarolva
		      // vektorelem: sztring -> "mi_eq; mivel_eq"
		      String[] eqs = eqs_teljes.split("\n");
		      Vector<String> eqs_fin = new Vector<String>();		      
		      // uj vektor (choices_ext), ami a Choices reszbol eloallitott lehetseges elemzeseket tartalmazza,  
		      // beepitve az Equivalences reszben levo ekvivalenciakkal
		      Vector<String> choices_ext = new Vector<String>();

		      // az opciok masolasa (hogy kesobb az ekvivalenciakat hozzaadjuk a masolathoz, es az eredeti "opciok" vektor valtozatlan marad)  
		      for (int l=0; l<opciok.size(); l++){
		    	  choices_ext.add(opciok.get(l));
		      }
		      	      
		      // a c-szerkezet kinyerese a c_str valtozoba
		      int elemz_fej = elemzes.indexOf("% C-Structure:");
		      int c_kezd = elemzes.indexOf("[", elemz_fej);
		      int c_veg = elemzes.indexOf("]).", elemz_fej);
		      //System.out.println(elemz_fej);
		      //System.out.println(c_kezd);
		      //System.out.println(c_veg);
		      		      
		      // az elso cf(...) sortol az utolsoig
		      c_str = elemzes.substring(c_kezd+2, c_veg-1);
		      //System.out.println(c_str);
		      
		      Vector<String> ts = new Vector<String>();
		      Vector<String> ts2 = new Vector<String>();
		      Vector<String> nts = new Vector<String>();
		      Vector<String> nts2 = new Vector<String>();
		      Vector<String> sff = new Vector<String>();
		      Vector<String> sfd = new Vector<String>();
		      Vector<String> fs = new Vector<String>();
		      Vector<String> phis = new Vector<String>();
		      
		      
		      //az egyes cf(...) sorok letarolasa egy tomb elemeikent,
		      String[] cfs = c_str.split("\n");
		      for (int x=0; x<cfs.length; x++){
		    	  cfs[x] = cfs[x].substring(3, cfs[x].lastIndexOf(')'));
		          //System.out.println("cf[" + x + "] =>\t" + cfs[x]);
		    	  
		    	  if (cfs[x].contains(",terminal")){
		    		  // ha egy csomopont tobb elemzes_azonositoval rendelkezik, akkor a csomopontot az osszes azonositoval, egyenkent taroljuk
		    		  String elemzes_azon = kinyer_kezdpoz(cfs[x], 0, "terminal");
		    		  if (elemzes_azon.contains("or(")==true){
			    		  elemzes_azon = elemzes_azon.replace("or(", "");
			    		  elemzes_azon = elemzes_azon.replace(")", "");
			    		  //System.out.println(masodik_resz);
			    	  }
		    		  String tobbi = cfs[x].substring(cfs[x].indexOf("terminal"));
		    		  //System.out.println("terminal[" + x + "] => " + elemzes_azon + tobbi);
		    		  //System.out.println("terminal[" + x + "] => " + cfs[x]);
		    		  String[] lehetosegek = elemzes_azon.split(",");
		    		  for (int i=0; i<lehetosegek.length; i++){  
			    		  ts2.add(lehetosegek[i] + "," + tobbi);
			    		  //System.out.println(lehetosegek[i] + "," + tobbi);
		    		  }
		    		  ts.add(cfs[x]);
		    		  //System.out.println(cfs[x]);
		    	  }
		    	  
		    	  else if (cfs[x].contains(",subtree")){
		    		  // ha egy csomopont tobb elemzes_azonositoval rendelkezik, akkor a csomopontot az osszes azonositoval, egyenkent taroljuk
		    		  String elemzes_azon = kinyer_kezdpoz(cfs[x], 0, "subtree");
		    		  if (elemzes_azon.contains("or(")==true){
			    		  elemzes_azon = elemzes_azon.replace("or(", "");
			    		  elemzes_azon = elemzes_azon.replace(")", "");
			    		  //System.out.println(masodik_resz);
			    	  }
		    		  String tobbi = cfs[x].substring(cfs[x].indexOf("subtree"));
		    		  //System.out.println("subtree[" + x + "] => " + elemzes_azon + tobbi);
		    		  //System.out.println("subtree[" + x + "] => " + cfs[x]);
		    		  String[] lehetosegek = elemzes_azon.split(",");
		    		  for (int i=0; i<lehetosegek.length; i++){
		    			  nts2.add(lehetosegek[i] + "," + tobbi);
		    		      //System.out.println(lehetosegek[i] + "," + tobbi);
		    		  }
		    		  nts.add(cfs[x]);
		    		  //System.out.println(cfs[x]);
		    	  }
		    	  
		    	  else if (cfs[x].contains(",surfaceform")){
		    		  //System.out.println("surfaceform[" + x + "] => " + cfs[x]);
		    		  sff.add(cfs[x]);
		    	  }
		    	  
		    	  else if (cfs[x].contains(",semform_data")){
		    		  //System.out.println("semform_data[" + x + "] => " + cfs[x]);
		    		  sfd.add(cfs[x]);
		    	  }
		    	  
		    	  else if (cfs[x].contains(",fspan")){
		    		  //System.out.println("fspan[" + x + "] => " + cfs[x]);
		    		  fs.add(cfs[x]);
		    	  }
		    	  
		    	  else if (cfs[x].contains(",phi(")){
		    		  String elemzes_azon = kinyer_kezdpoz(cfs[x], 0, "phi");
		    		  //System.out.println(elemzes_azon);
		    		  if (elemzes_azon.contains("or(")==true){
			    		  elemzes_azon = elemzes_azon.replace("or(", "");
			    		  elemzes_azon = elemzes_azon.replace(")", "");
			    	  }
		    		  //System.out.println(elemzes_azon);
		    		  String darabolando_phi = new String();
		    		  darabolando_phi = kinyer(cfs[x], "phi(", ")").trim();
		    		  //System.out.println(darabolando_phi);
		    		  String[] szetszed_phi = darabolando_phi.split(",");
		    		  //System.out.println(szetszed_phi[0] + "," + szetszed_phi[1].substring(4));
		    		  String[] lehetosegek = elemzes_azon.split(",");
		    		  for (int i=0; i<lehetosegek.length; i++){
		    			  phis.add(lehetosegek[i] + "," + szetszed_phi[0] + "," + szetszed_phi[1].substring(4));
		    			  //System.out.println(lehetosegek[i] + "," + szetszed_phi[0] + "," + szetszed_phi[1].substring(4));
		    		  }
		    		  
		    	  }
		    	  
		      }
		      
		      //System.out.println("Non-terminals: " + nts.size() + "\tNon-terminals processed: " + nts2.size());
		      //System.out.println("Terminals: " + ts.size() + "\tTerminals processed: " + ts2.size());
		      
		      Vector<String> relevant_eqs = new Vector<String>();
		      // relevans CV_xxx cimkes csomopontok felkutatasa -- eleg csak ez(eke)t feldolgozni az Equivalences reszbol!
		      for (int x=0; x<nts2.size(); x++){
		    	  String elemzescimke = kinyer_kezdpoz(nts2.get(x), 0, ",");
		    	  if (elemzescimke.contains("CV_") && !relevant_eqs.contains(elemzescimke))
		    		  relevant_eqs.add(elemzescimke);  	  		    	  
		      }
		      
		      
		      // csak azokat az equivalences sorokat dolgozzuk fel, amik relevansak (adott CV_xxx szerepel, mint csomoponti elemzes-azonosito)
	    	  		//System.out.println("\nEQUIVALENCES:");
		      if (!eqs_teljes.isEmpty()){
		    	  for (int x=0; x<eqs.length; x++){
		    		  // csak a zarojelezett resz megtartasa -- "define" szoveg es sor vegi irasjel eltavolitasa
			    	  eqs[x] = eqs[x].substring(7, eqs[x].lastIndexOf(')'));
			    	  // az elso resz kulonszedese (CV_xxx)
			    	  String mi_eq = kinyer_kezdpoz(eqs[x], 0, ",");
			    	  // kizarolag akkor dolgozzuk fel a maradek reszt, ha az adott CV_xxx elemzes-azonositot fell kell dolgoznunk
			    	  if (relevant_eqs.contains(mi_eq)){
			    	  
				    	  //System.out.print("\t" + mi_eq);
				    	  // a masodik resz kinyerese -- it vannak az "or" operatoros kifejezesek
				    	  String mivel_eq = eqs[x].substring(eqs[x].indexOf(",")+2);
				    	  // az OR-reszek surithetok (?)
				    	  if (mivel_eq.contains("or(")==true){
				    		  mivel_eq = mivel_eq.replace("or(", "");
				    		  mivel_eq = mivel_eq.replace(")", "");
				    		  //System.out.println("\t" + mivel_eq);
				    	  }
				    	  String[] darabolt_mivel = mivel_eq.split(",");
				    	  for (int i=0; i<darabolt_mivel.length; i++){
				    		  for (int j=0; j<opciok.size(); j++){
				    			  //System.out.println("** " +choices_ext.get(j));
				    			  String t_choices = "\"" + choices_ext.get(j).replace(",", "\",\"") + "\"";
				    			  //System.out.println(t_choices);
/*HIBA --jav; WARNING: contains a mi_eq vÄ‚Ë‡ltozÄ‚Ĺ‚ra*/				    			  
				    			  if (t_choices.contains("\"" + darabolt_mivel[i] + "\"") && !choices_ext.contains(opciok.get(j) + "," + mi_eq)){
				    				  //System.out.println(opciok.get(j));
				    				  choices_ext.set(j, choices_ext.get(j) + "," + mi_eq);
				    				  //System.out.println(choices_ext.get(j));
				    			  }
				    		  }
				    	  }
			    		  eqs_fin.add(mi_eq + "; " + mivel_eq);
			    		  //System.out.println("\t" + mi_eq + "; " + mivel_eq);
			    		  
			    	  }
			    	  
		    	  }
		      }
		      // AZ EKVIVALENCIÁK KIÍRÁSA
		      //System.out.println("\nEQUIVALENCES processed:");
	    	  //for (int h=0; h<choices_ext.size(); h++)
	    	  //	  System.out.println("\t" + choices_ext.get(h));
	    	  //
		      //System.out.println("\nA VEKTOR MÉRETE: " + choices_ext.size());     
		      
		      if (choices_ext.size() != solution){
		    	  System.err.println("PROBLEM: the number of solutions does not equal to the number of solutions given in the EQUIVALENCES section.");
		    	  //return false;
		      }
	      
		      // az "<a> ... </a>" reszek
		      //System.out.println("<s id=\"s" + Integer.toString(mondatszamol) + "\" txt=\"" + mondat + "\">\n<pl>\n" + elemz + "</pl>\n");
		      int check_analysis_num = 0;
		      int bad_analysis_count = 0;
		      int good_a_counter = 0;
		      //into_file = "<s id=\"s" + Integer.toString(mondatszamol) + "\">\n<pl>\n" + elemzes + "</pl>\n";
		      into_file = "<s id=\"s" + Integer.toString(mondatszamol) + "\" text=\"" + mondat +  "\">\n";
		      ArrayList<String> a_to_write = new ArrayList<String>();
		      for (int a=0; a<choices_ext.size(); a++){
		    	  ++good_a_counter;
		    	  ++check_analysis_num;
		    	  //System.out.println(choices_ext.get(a));
		    	  //System.out.println("A: " + (a+1) + " ###");
		    	  //Vector<Csomopont2> terminals = new Vector<Csomopont2>();
		    	  //Vector<Csomopont2> nonterminals = new Vector<Csomopont2>();
		    	  Vector<Csomopont2> csp_vekt = new Vector<Csomopont2>();
		    	  
		    	  String csp = new String();
		    	  for (int b=0; b<nts2.size(); b++){
		    		  String elemzes_azon = kinyer_kezdpoz(nts2.get(b), 0, ",subtree");
		    		  // ha az adott csomopont szerepelhet az adott elemzesben...
		    		  String[] elemleh_darab = choices_ext.get(a).split(",");
/*HIBA  --jav*/		    		  //if (choices_ext.get(a).contains(elemzes_azon)){
	    		      int szuks_csp=0;
	    		      for (int elc=0; elc<elemleh_darab.length; elc++){
	    		    	 //System.out.print(elemleh_darab[elc]+",");
	    		    	 if (elemleh_darab[elc].equals(elemzes_azon)){
	    		    		 //System.out.println(elemleh_darab[elc] + " " + elemzes_azon);
	    		    		 szuks_csp=1;
	    		    	 }
	    		      }
	    		      //System.out.println();
	    			  if (szuks_csp==1){
		    		      csp = kinyer(nts2.get(b), "subtree(", ")");
		    		      //System.out.println(nts2.get(b));
		    		      //System.out.println(elemzes_azon);
		    			  String[] darabol = csp.split(",");
		    			  darabol[1] = darabol[1].substring(1, darabol[1].lastIndexOf("'"));
		    			  //System.out.println(darabol[0] + "\t" + darabol[1]+ "\t" + darabol[2]+ "\t" + darabol[3]);
		    			  String phi_var = new String();
		    			  for (int ph=0; ph<phis.size(); ph++){
		    				  String[] phi_reszek = phis.get(ph).split(",");
		    				  if (phi_reszek[1].equals(darabol[1]) && elemzes_azon.equals(phi_reszek[0])){
		    					  phi_var = phi_reszek[2];
		    				  }
		    			  }
		    			  //System.out.println(darabol[0]);
		    			  Vector<String> gyerekek = new Vector<String>();
		    			  gyerekek.add(darabol[2]);
		    			  gyerekek.add(darabol[3]);
		    			  Csomopont2 ntcsp = new Csomopont2(darabol[0], darabol[1], gyerekek, 0, phi_var);
		    			  //System.out.println(ntcsp.toString());
		    			  csp_vekt.add(ntcsp);
		    		  }
		    	  }
		    	  for (int c=0; c<ts2.size(); c++){
		    		  String elemzes_azon = kinyer_kezdpoz(ts2.get(c), 0, ",terminal");
		    		  // ha az adott csomopont szerepelhet az adott elemzesben...
		    		  String[] elemleh_darab = choices_ext.get(a).split(",");
/*HIBA --jav*/		    		  //if (choices_ext.get(a).contains(elemzes_azon)){
	    		      int szuks_csp=0;
	    		      for (int elc=0; elc<elemleh_darab.length; elc++){
	    		    	 //System.out.print(elemleh_darab[elc]+",");
	    		    	 if (elemleh_darab[elc].equals(elemzes_azon)){
	    		    		 //System.out.println(elemleh_darab[elc] + " " + elemzes_azon);
	    		    		 szuks_csp=1;
	    		    	 }
	    		      }
	    		      //System.out.println();
	    			  if (szuks_csp==1){
/*HIBA --jav*/    			  //csp = kinyer(ts2.get(c), "terminal(", ")");
		    			  csp = ts2.get(c).substring(ts2.get(c).indexOf("terminal(")+9, ts2.get(c).lastIndexOf(")"));
		    			  //System.out.println("0 ###########  " + csp + "  ########### 0");
		    			  
		    			  String darabol0 = csp.substring(0, csp.indexOf(","));
		    			  //System.out.println("   0 *" + darabol0 + "*");
		    			  String darabol1 = csp.substring(csp.indexOf(",")+2, csp.lastIndexOf(",")-1);
		    			  //System.out.println("   1 *" + darabol1 + "*");
/*HIBA --jav --ha '[' vagy ']' a szóalak, vagy van benne ilyen karakter*/	
		    			  //String darabol2 = csp.substring(csp.indexOf("[")+1, csp.indexOf("]"));	    			  
		    			  String darabol2 = csp.substring(csp.indexOf("[", csp.lastIndexOf(","))+1, csp.indexOf("]", csp.lastIndexOf(",")));
		    			  
		    			  //System.out.println("   2 *" + darabol2 + "*");
		    			  
		    			  //System.out.println("#1: ***"+ darabol0 + "***" + darabol1+ "***" + darabol2 + "***");
		    			  //String[] darabol = csp.split(",");
		    			  //darabol[1] = darabol[1].substring(1, darabol[1].lastIndexOf("'"));		    			  
		    			  //darabol[2] = darabol[2].substring(darabol[2].indexOf("[")+1, darabol[2].indexOf("]"));
		    			  //System.out.println("#1: ***"+ darabol[0] + "***" + darabol[1]+ "***" + darabol[2] + "***");
		    			  String phi_var = new String();
		    			  for (int ph=0; ph<phis.size(); ph++){
		    				  String[] phi_reszek = phis.get(ph).split(",");
		    				  if (phi_reszek[1].equals(darabol0) && elemzes_azon.equals(phi_reszek[0])){
		    					  //System.out.println(phi_reszek[1] + " = " + darabol0);
		    					  phi_var = phi_reszek[2];
		    				  }
		    			  }
		    			  Vector<String> gyerekek = new Vector<String>();
		    			  Csomopont2 tcsp = new Csomopont2 (darabol0, darabol1, gyerekek, 1, phi_var);
		    			  //System.out.println(tcsp.toString());
		    			  tcsp.alakszam = darabol2;
		    			  csp_vekt.add(tcsp);		    		  
		    		  }
		    	  }
		    	  
		    	  
		    	  // lehetseges gyokerek megkeresese
		    	  Vector<Csomopont2> gyoker_lehet = new Vector<Csomopont2>();
		    	  for (int p=0; p<csp_vekt.size(); p++){
		    		  Csomopont2 aktualis_csp = csp_vekt.get(p);
		    		  //System.out.println(aktualis_csp.csp_id + "," + aktualis_csp.cimke + "," + aktualis_csp.gyerekei);
		    		  if (aktualis_csp.cimke.equals(rootcat)){
		    			  //System.out.println(aktualis_csp.toString());
		    			  gyoker_lehet.add(aktualis_csp);
		    		  }
		    	  }
		    	  
		    	  // az osszes csomopontot tartalmazo vektor bejarasa: a gyoker lehetosegek vizsgalata, hogy van-e csomopont, aminek gyereke; ha igen, nem gyoker
		    	  Csomopont2 gyoker = new Csomopont2();  
		    	  for (int q=0; q<gyoker_lehet.size(); q++){
		    		  int stop = 0;
		    		  int kilep = 0;
		    		  String akt_gyok_id = gyoker_lehet.get(q).csp_id;
		    		  for (int r=0; r<csp_vekt.size(); r++){
		    			  //String akt_csp_gyerek1 = csp_vekt.get(r).getGyerek1();
		    			  //String akt_csp_gyerek2 = csp_vekt.get(r).getGyerek2();
		    			  //int aktmeret = csp_vekt.get(r).gyerekei.size();
		    			  for (int l=0; l<csp_vekt.get(r).gyerekei.size(); l++){
		    				  if (csp_vekt.get(r).gyerekei.get(l).equals(akt_gyok_id)){
		    					  kilep = 1;
			    				  break;
			    			  }
		    			  }
		    			  if (kilep == 1){
		    				  break;		    				  
		    			  }
		    			  else {
		    				  stop++;
		    			  }
		    		  }
		    		  //System.out.println("stop--> " + stop + "\t" + "csp_vekt.size--> " + csp_vekt.size());
		    		  if (stop == csp_vekt.size()){
		    			  //System.out.println("A GYÖKÉRELEM: " + gyoker_lehet.get(q).toString());
		    			  gyoker = gyoker_lehet.get(q); //new Csomopont2(gyoker_lehet.get(q).csp_id, gyoker_lehet.get(q).cimke, gyoker_lehet.get(q).gyerekei, 0, gyoker_lehet.get(q).phi);
		    			  //System.out.println(gyoker_lehet.get(q).gyerekei.get(1));
		    		  }
		    	  }
		    	  
		    	  Vector<Csomopont2> teljesfa = new Vector<Csomopont2>();
		    	  teljesfa.add(gyoker);
		    	  
		    	  //System.out.println("csp_vekt.size(): " + csp_vekt.size());		    	  
		    	  //System.out.println(gyoker.toString());
		    	  //while (csp_vekt.size() != teljesfa.size()){
		    		  for (int r1=0; r1<teljesfa.size(); r1++){
		    			  //System.out.println("\nr1: "+r1);
		    			  Csomopont2 tfget = teljesfa.get(r1);
		    			  Csomopont2 balgyerek = new Csomopont2();
		    			  int balset = 0;
	    				  Csomopont2 jobbgyerek = new Csomopont2();
	    				  int jobbset = 0;
	    				  //System.out.println(tfget.csp_id + ", " + tfget.cimke);
	    				  //System.out.println(tfget.gyerekei.toString());
		    			  for (int r2=0; r2<csp_vekt.size(); r2++){
		    				  
		    				  Csomopont2 cspvektget = csp_vekt.get(r2);
		    				  //System.out.println(tfget.gyerekei.toString());
		    				  if (tfget.gyerekei.size() != 0){ // a terminalisok eseteben nincsenek gyerekek!!!
			    				  if (tfget.gyerekei.get(0).equals(cspvektget.csp_id)){
			    					  //System.out.print(" bal: " + cspvektget.csp_id);
			    					  balgyerek = cspvektget;
			    					  balset = 1;
			    					  //csp_vekt.removeElementAt(r2);
			    				  }
			    				  /*else*/ if (tfget.gyerekei.get(1).equals(cspvektget.csp_id)){
			    					  //System.out.print(" jobb: "  + cspvektget.csp_id);
			    					  jobbgyerek = cspvektget;
			    					  jobbset = 1;
			    					  //csp_vekt.removeElementAt(r2);
			    				  }
			    				  //System.out.print("\n");
		    				  }
   				  		  }
		    			  
		    			  if (balset == 1  && jobbset == 1){
		    				  teljesfa.insertElementAt(jobbgyerek, r1+1);
		    				  teljesfa.insertElementAt(balgyerek, r1+1);
		    			  }
		    			  else if (balset == 1 && jobbset == 0){
		    				  teljesfa.insertElementAt(balgyerek, r1+1);
		    			  }
		    			  else if (balset == 0 && jobbset == 1){
		    				  teljesfa.insertElementAt(jobbgyerek, r1+1);
		    			  }
		    		  }
		      	  //}
		    	  //System.out.println("teljesfa.size(): " + teljesfa.size());
		    	  
			    	  if (csp_vekt.size()!=teljesfa.size()){
			    		  for (int fck=0; fck<csp_vekt.size(); fck++){
			    			  int megvan=0;
			    			  for (int fck2=0; fck2<teljesfa.size(); fck2++){
			    				  //if (teljesfa.get(fck2).cimke.contains("ROOT"))
			    					  //System.out.println(teljesfa.get(fck2));
			    				  if (csp_vekt.get(fck).equals(teljesfa.get(fck2))){
				    				  megvan=1;
				    				  break;
			    				  }
			    			  }
			    			  if (megvan==0){
			    				  System.err.println("PROBLEM: Vectors of different sizes when building tree. Missing: " + csp_vekt.get(fck));
			    				  ;
			    			  }
			    		  }
			    	  }
		    	  
		    	  for (int k=0; k<teljesfa.size(); k++){
		    		  Csomopont2 aktcsp = teljesfa.get(k);
		    		  if (aktcsp.gyerekei.indexOf("-")>=0)
		    			  aktcsp.gyerekei.removeElementAt(aktcsp.gyerekei.indexOf("-"));
		    			  

		    		  if (k>0){
		    			  Csomopont2 elozocsp = teljesfa.get(k-1);
		    			  // ha az aktualis csomopont cimkeje es phi azonositoja azonos az elozo csomoponteval, akkor toroljuk, a gyerekeit pedig felcsusztatjuk
		    			  if (aktcsp.getCimke().equals(elozocsp.getCimke()) && aktcsp.getPhi().equals(elozocsp.getPhi())){
		    				  Vector<String> aktgyerekei = aktcsp.getGyerekei();
		    				  //System.out.println(aktcsp.toString());
		    				  // ha azonos cimkejuek, akkor a bal oldara van ismetlodes, ez elso gyerek, es ez torlendo
		    				  elozocsp.gyerekei.removeElementAt(0);
		    				  int l;
		    				  for (l=aktgyerekei.size()-1; l>=0; l--){
		    					  elozocsp.gyerekei.add(0, aktgyerekei.get(l));
		    					  //System.out.print("\t" + aktgyerekei.get(l));
		    				  }
		    				  teljesfa.setElementAt(elozocsp, k-1);
		    				  teljesfa.removeElementAt(k);
		    				  k--;
		    				  //System.out.println(aktcsp.toString());
		    				  //System.out.println(elozocsp.toString());
		    			  }
		    			  
		    			  // ha a cimke kategoria-bazis, akkor csak egy gyereke van, ami a lemma
		    			  String lemma = new String();
		    			  String jegyek = new String();
		    			  String alak_szam = new String();
		    			  String alak = new String();
		    			  if ((aktcsp.getCimke().contains("_BASE")) && !aktcsp.getCimke().contains("_SFX_BASE")){
		    				  Vector<String> aktgyerekei = aktcsp.getGyerekei();		    				 
		    				  //megkeressuk azt a csomopontot, amelyik a gyereke, ennek a cimkeje a lemma
		    				  for (int z=0; z<teljesfa.size(); z++){
		    					  if (aktgyerekei.get(0).equals(teljesfa.get(z).getCsp_id())){
		    						  lemma = teljesfa.get(z).getCimke();
		    						  alak_szam = teljesfa.get(z).getAlakszam();
		    						  // az alak kinyerÄ‚Â©se a surfaceform rÄ‚Â©szekbÄąâ€�l
		    						  for (int z2=0; z2<sff.size(); z2++){			    							  
		    							  String akt_e_azon = kinyer(sff.get(z2), "surfaceform(", ",'");	
		    							  //System.out.println("surface form: " + sff.get(z2));
		    					    	  String akt_alak = //kinyer(sff.get(z2), ",'", "',");
		    					    		  sff.get(z2).substring(sff.get(z2).indexOf(",'")+2, sff.get(z2).lastIndexOf("',"));			    					    	  
		    					    	  if (akt_e_azon.equals(alak_szam)){
		    					    		  alak = akt_alak;		    					    		  
		    					    		  break;
		    					    	  }
		    						  }
		    						  teljesfa.removeElementAt(z);
		    						  break;
		    					  }
		    				  }
		    				  //kiszedjuk a morfologiai jegyeket
		    				  for (int v=0; v<teljesfa.size(); v++){
		    					  if (alak_szam.equals(teljesfa.get(v).getAlakszam()) && !teljesfa.get(v).getCimke().equals(lemma)){
		    						  jegyek = jegyek + " " + teljesfa.get(v).getCimke();
		    						  //System.out.println("-> " + teljesfa.get(v).toString());
		    						  //System.out.println(elozocsp.toString());
		    						  teljesfa.removeElementAt(v);		    						  
		    					  }
		    				  }
		    				  elozocsp.gyerekei.removeAllElements();
		    				  elozocsp.gyerekei.add(aktcsp.getCsp_id());

		    				  aktcsp.megjegyzes = jegyek.trim();
    						  aktcsp.alak_ha_term = alak;
    						  aktcsp.cimke = alak;
    						  aktcsp.lemma_ha_term = lemma;
    						  aktcsp.terminalis_e = 2;
   							  aktcsp.gyerekei.set(0, "");
    						  
		    				  //System.out.println("-> " +aktcsp.toString());
		    				  //System.out.println(alak + "\t" + lemma + "\t" + alak_szam + "\t" +jegyek);
		    				  //System.out.println(elozocsp.toString());
		    			  }		    			  
		    		  }
		    		  //System.out.println(aktcsp.toString());
		    	  }
		    	  
		    	  for (int k=0; k<teljesfa.size(); k++){
		    		  if (teljesfa.get(k).getCimke().contains("_BASE")){
		    			  //System.out.println("--->  " + teljesfa.get(k).toString());
		    			  teljesfa.removeElementAt(k);
		    			  k--;
		    		  }
		    		  
		    	  }
		    	  
		    	  //clear empty (child)references
		    	  for (int abc=0; abc<teljesfa.size(); ++abc){
		    		  for (int abc2=0; abc2<teljesfa.get(abc).gyerekei.size(); ++abc2){
		    			  if (teljesfa.get(abc).gyerekei.get(abc2).isEmpty()){
		    				  //System.out.println("NEM jó!: " + teljesfa.get(abc));
		    				  teljesfa.get(abc).gyerekei.removeElementAt(abc2);
		    				  --abc2;
		    				  //System.out.println("jó!: " + teljesfa.get(abc));
		    			  }
		    		  }
		    	  }
		    	  
		    	  //remove link to children that have been deleted in the previous stem (_BASE)...
		    	  for (int cnt0=0; cnt0<teljesfa.size(); cnt0++){
    				  //Vector<String> gykei = new Vector<String>();
    				  //gykei = teljesfa.get(cnt0).gyerekei;
    				  //System.out.println(gykei);
    				  
    				  //System.out.println("{" + teljesfa.get(cnt0).gyerekei.size() + "}\t" + teljesfa.get(cnt0));
    				  for (int cnt1=0; cnt1<teljesfa.get(cnt0).gyerekei.size(); ++cnt1){
    					  //System.out.println("\t[" + cnt1 + "] " + teljesfa.get(cnt0).gyerekei.get(cnt1));
    					  int megvan = 0;
    					  for (int cnt2=0; cnt2<teljesfa.size(); ++cnt2){
    						  //System.out.println("\t\thasonlít: " + teljesfa.get(cnt0).gyerekei.get(cnt1) + " & " + teljesfa.get(cnt2).csp_id);
    						  if (teljesfa.get(cnt0).gyerekei.get(cnt1).equals(teljesfa.get(cnt2).csp_id)){
    							  megvan = 1;
    							  break;
    						  }
    					  }
    					  if (megvan==0){
    						  teljesfa.get(cnt0).gyerekei.removeElementAt(cnt1);
    						  --cnt1;
    					  }
    				  }
    			  }
		    	  
		    	  
		    	  int maxmelyseg = 0;
		    	  for (int k=0; k<teljesfa.size(); k++){
		    		  //System.out.println(teljesfa.get(k));
		    		  if (k==0){
		    			  teljesfa.get(k).melyseg=0;
		    		  }
		    		  else {
		    			  for (int kbelso=0; kbelso<teljesfa.size(); kbelso++){
		    				  if (teljesfa.get(kbelso).gyerekei.contains(teljesfa.get(k).csp_id)){
		    					  //System.out.println("igen: " + teljesfa.get(kbelso).toString2());
		    					  teljesfa.get(k).melyseg=(teljesfa.get(kbelso).melyseg)+1;
		    					  if (maxmelyseg<teljesfa.get(k).melyseg)
		    						  maxmelyseg=teljesfa.get(k).melyseg;
		    					  break;
		    				  }
		    			  }
		    		  }
		    			  
		    		  //System.out.println(teljesfa.get(k).toString2());
		    	  }
		    	  
		    	  Collections.reverse(teljesfa);
		    	  
		    	  String terminalisok = new String();
		    	  String nemterminalisok = new String();
		    	  String aktelemzes = new String();
		    	  aktelemzes = "<a id=\"" + (good_a_counter) + "\" t=\"a\" c=\"u\" root=\"s" + Integer.toString(mondatszamol) + "_" + gyoker.getCsp_id() + "\" td=\"" + maxmelyseg + "\" tw=\"" + sff.size() + "\">\n";
		    	  int terminalisok_count = 0;
		    	  for (int k=0; k<teljesfa.size(); k++){
		    		  //System.out.println(teljesfa.get(k).toString());
		    		  Csomopont2 acs = teljesfa.get(k);
		    		  
		    		  // ADDED TO STORE THE PATH (! from termial to ROOT cat)
		    		  String acsp_cat = acs.cimke;
		    		  String acsp_id = acs.csp_id;
		    		  String path = "";
		    		  if (acs.terminalis_e == 1 || acs.terminalis_e == 2){
		    			  //System.out.println("# " + acs.cimke + " #");
		    			  while (!acsp_cat.equals("ROOT")){
		    				  for (int pd1=0; pd1<teljesfa.size(); ++pd1){
		    					  Csomopont2 pdetcsp = teljesfa.get(pd1);
		    					  if (pdetcsp.gyerekei.contains(acsp_id)){
		    						  path = path + pdetcsp.cimke;
		    						  acsp_cat = pdetcsp.cimke;
		    						  if (!acsp_cat.equals("ROOT"))
		    							  path= path + ";";
		    						  acsp_id = pdetcsp.csp_id;
		    					  }
		    				  }
		    			  }
		    			  //System.out.println(path);
		    		  }
		    		  //
		    		  
		    		  if (acs.terminalis_e == 2){
		    			  terminalisok = terminalisok + "<t id=\"s" + Integer.toString(mondatszamol) + "_" + acs.getCsp_id() + "\" word=\"" +acs.getAlak() + "\" lem=\"" + acs.getLemma() + "\" morph=\"" + acs.getMegjegyzes() + "\" path=\"" + path + "\" nd=\"" + acs.melyseg + "\" />\n";
		    			  ++terminalisok_count;
		    			  if (acs.getAlak().equals("") || acs.getAlak().indexOf("[")>-1 || acs.getAlak().indexOf("]")>-1){
		    				  terminalisok_count = -1;
		    			  }
		    		  }
		    		  else if (acs.terminalis_e == 1){
		    			  terminalisok = terminalisok + "<t id=\"s" + Integer.toString(mondatszamol) + "_" + acs.getCsp_id() + "\" word=\"" +acs.getCimke() + "\" lem=\"" + acs.getCimke() + "\" morph=\"" + acs.getMegjegyzes() + "\" path=\"" + path + "\" nd=\"" + acs.melyseg + "\" />\n";
		    			  ++terminalisok_count;
		    			  if (acs.getCimke().equals("") || acs.getCimke().indexOf("[")>-1 || acs.getCimke().indexOf("]")>-1){
		    				  terminalisok_count = -1;
		    			  }
		    		  }
		    		  else {
		    			  nemterminalisok = nemterminalisok + "<nt id=\"s" + Integer.toString(mondatszamol) + "_" + acs.getCsp_id() + "\" cat=\"" + acs.getCimke() + "\" nd=\"" + acs.melyseg + "\">\n";;
		    			  for (int gy=0; gy<acs.getGyerekei().size(); gy++){
		    				  nemterminalisok = nemterminalisok + "<edge idref=\"s" +Integer.toString(mondatszamol) + "_" + acs.gyerekei.get(gy) + "\" />\n";
		    			  }
		    			  nemterminalisok = nemterminalisok + "</nt>\n";
		    		  }
		    	  }
		    	  
		    	  String brr = "[" + gyoker.cimke;
		    	  for (int gygyc=0; gygyc<gyoker.gyerekei.size(); ++gygyc)
		    		  brr = brr + " [#" + gyoker.gyerekei.get(gygyc) + "#]";
		    	  brr = brr + "]";
		    	  
	    		  while (brr.indexOf("#")>-1){
		    		  int firsthash = brr.indexOf("#")+1;
		    		  int secondhash = brr.indexOf("#", firsthash);
		    		  String kendo = brr.substring(firsthash, secondhash);
		    		  //System.out.println(brr);
		    		  //System.out.println("keres: " + kendo);
		    		  for (int brc=0; brc<teljesfa.size(); ++brc){
		    			  Csomopont2 acs = teljesfa.get(brc);
		    			  //System.out.println("\t" + acs.csp_id + "\t" + acs.cimke + "\t" +acs.gyerekei + "\t" + acs.gyerekei.size() + "\t" + acs.terminalis_e);
		    			  if (kendo.equals(acs.csp_id)){
		    				  String agyerekek=" ";
		    				  if (acs.terminalis_e!=1 && acs.terminalis_e!=2){
		    					  for (int acsgy=0; acsgy<acs.gyerekei.size(); ++acsgy){
		    						  agyerekek = agyerekek + "[#" + acs.gyerekei.get(acsgy) + "#]";
		    					  }
		    				  }
		    				  if (acs.terminalis_e==1){
		    					  //System.out.println(acs);
		    					  agyerekek = agyerekek + "[ " + acs.getCimke() + "]";
		    						
		    				  }		    				  
		    				  if (acs.terminalis_e==2){
		    					  //System.out.println(acs);
		    					  agyerekek = agyerekek + "[ " + acs.lemma_ha_term + " " + acs.megjegyzes + "]";
		    						
		    				  }
		    				  brr = brr.replace("#" + acs.csp_id + "#", acs.cimke + agyerekek);
		    				  break;
		    			  }
		    		  }
		    		  
		    	  }
	    		  //System.out.println(brr);
	    		  //System.out.println("surface forms: " + sff.size() + "\tterminals: " + terminalisok_count);
	    		  
	    		  if (sff.size() != terminalisok_count){
	    			  //System.out.println("surface forms: " + sff.size() + "\tterminals: " + terminalisok_count);
	    			  ++bad_analysis_count;
	    			  --good_a_counter;
	    			  continue;
	    		  }	    	
	    		  
	    		  // to check whether an analysis that is the same had already been processed, in order to avoid duplicates
	    		  int a_already_exists = 0;
	    		  for (int atwc=0; atwc< a_to_write.size(); ++atwc){
	    			  if (a_to_write.get(atwc).equals(brr)){
	    				  a_already_exists = 1;
	    				  --good_a_counter;
	    				  break;
	    			  }
	    		  }
	    		  
	    		  if ( a_already_exists == 0){
	    			  a_to_write.add(brr);
	    		  }
	    		  
	    		  else if (a_already_exists == 1) {
	    			  continue;
	    		  }
	    		  
		    	  aktelemzes = aktelemzes + "<terminals>\n" + terminalisok + "</terminals>\n<nonterminals>\n" + nemterminalisok + "</nonterminals>\n<bracketed>" + brr + "</bracketed>\n</a>\n";
		    	  //System.out.println(terminalisok + nemterminalisok);
		    	  //System.out.println(aktelemzes);
		    	  into_file = into_file + aktelemzes;
		    	  
		    	  kimenet.flush();
		    	  //System.out.println("\n");
		      }
		      
		      a_to_write.clear();
		      
		      into_file = into_file + "</s>\n\n";
		      
		      if (bad_analysis_count != check_analysis_num){
		    	  kimenet.write(into_file);
				  kimenet.flush();
		      }
		      else {
		    	  //System.out.println("eldobva");
		    	  --mondatszamol;
		      }
	  
		      beelemz.close();  
     
	}
}
kimenet.close();
}
		    catch (FileNotFoundException exc){
		      System.err.println("File could not be found");
		      System.exit(1);
		    }
		    
		    catch (IOException exc){
		      System.err.println("I/O error");
		      exc.printStackTrace();
		      System.exit(2);
		    }
		    
	 }
	 
}
