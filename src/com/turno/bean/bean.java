package com.turno.bean;

public class bean {
	
	private static String nombreDependencia;
	private static String idEmpresa;
	
	public static String getNombreDependencia() {
		return nombreDependencia;
	}

	public static void setNombreDependencia(String nombreDependencia) {
		bean.nombreDependencia = nombreDependencia;
	}

	public static String getIdEmpresa() {
		return idEmpresa;
	}

	public static void setIdEmpresa(String nombreEmpresa) {
		bean.idEmpresa = nombreEmpresa;
	}

}
