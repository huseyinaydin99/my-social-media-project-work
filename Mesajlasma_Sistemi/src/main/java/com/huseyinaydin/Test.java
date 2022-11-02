package com.huseyinaydin;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "test1")
@RequestScoped
public class Test {
	private ArrayList<Kisi> kisiList;
	
	public Test() {
		super();
		this.kisiList = new ArrayList<>();
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
		kisiList.add(new Kisi("Hüseyin",new Departman("Abc")));
	}

	public ArrayList<Kisi> getKisiList() {
		return kisiList;
	}

	public void setKisiList(ArrayList<Kisi> kisiList) {
		this.kisiList = kisiList;
	}

	public class Kisi{
		private String isim;
		
		private Departman departman;
		
		public Kisi(String isim,Departman departman) {
			super();
			this.isim = isim;
			this.departman = departman;
		}

		public String getIsim() {
			return isim;
		}

		public void setIsim(String isim) {
			this.isim = isim;
		}

		public Departman getDepartman() {
			return departman;
		}

		public void setDepartman(Departman departman) {
			this.departman = departman;
		}
		
	}
	
	public class Departman{
		private String departmanAdi;

		public Departman(String departmanAdi) {
			super();
			this.departmanAdi = departmanAdi;
		}

		public String getDepartmanAdi() {
			return departmanAdi;
		}

		public void setDepartmanAdi(String departmanAdi) {
			this.departmanAdi = departmanAdi;
		}
	}
}
