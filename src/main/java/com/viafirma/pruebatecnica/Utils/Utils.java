package com.viafirma.pruebatecnica.Utils;

import java.util.UUID;

public class Utils {
	
	/**
	 * Genera nuevo nombre de archivo
	 * @param originName nombre del archivo original
	 * @return
	 */
	public static String getFileName(String originName) {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
