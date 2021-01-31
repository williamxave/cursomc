package com.williambohn.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class Url {
		
	//Metodo que pega os ids da url que sao uma string e transforma em uma list de inteiros
		public static List<Integer> decodeIntList(String s) {
			String [] vet = s.split(","); // split recorta uma string baseada no caracter que voc passou 
			List<Integer> list = new ArrayList<>();
			for(int i = 0; i < vet.length; i++) {
				list.add(Integer.parseInt(vet[i]));
			}
			return list;
			
			/*
			 * Todo esse método pode ser feito em apenas uma linha com lambda.
			 * 
			 * return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
			 */
		}
		
		public static String decodeParam(String s) {
			try {
				return URLDecoder.decode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "";
			}
		}
}
