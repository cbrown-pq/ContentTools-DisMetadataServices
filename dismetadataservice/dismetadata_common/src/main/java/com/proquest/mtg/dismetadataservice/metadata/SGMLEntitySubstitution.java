package com.proquest.mtg.dismetadataservice.metadata;

import java.util.List;

import com.google.common.collect.ImmutableList;

public class SGMLEntitySubstitution {
	
	private final String entity;
	private final String substitution;
	
	public SGMLEntitySubstitution(String entity, String substitution) {
		this.entity = entity;
		this.substitution = substitution;
	}
	
	public String getEntity() {
		return entity;
	}
	
	public String getSubstitution() {
		return substitution;
	}
	
	public String applyTo(String x) {
		return x.replaceAll(getEntity(), getSubstitution());
	}
	
	public static final List<SGMLEntitySubstitution> 
			kSGMLEntitySubstitution = ImmutableList.of(
				new SGMLEntitySubstitution("&Aacute;", "A"),
				new SGMLEntitySubstitution("&aacute;", "a"),
				new SGMLEntitySubstitution("&Abreve;", "A"),
				new SGMLEntitySubstitution("&abreve;", "a"),
				new SGMLEntitySubstitution("&Acirc;", "A"),
				new SGMLEntitySubstitution("&AElig;", "AE"),
				new SGMLEntitySubstitution("&aelig;", "ae"),
				new SGMLEntitySubstitution("&Agrave;", "A"),
				new SGMLEntitySubstitution("&agrave;", "a"),
				new SGMLEntitySubstitution("&alpha;", "alpha"),
				new SGMLEntitySubstitution("&Amacr;", "a"),
				new SGMLEntitySubstitution("&amacr;", "a"),
				new SGMLEntitySubstitution("&angst;", "A"),
				new SGMLEntitySubstitution("&Aogon;", "A"),
				new SGMLEntitySubstitution("&aogon;", "a"),
				new SGMLEntitySubstitution("&Aring;", "A"),
				new SGMLEntitySubstitution("&aring;", "a"),
				new SGMLEntitySubstitution("&Atilde;", "A"),
				new SGMLEntitySubstitution("&atilde;", "a"),
				new SGMLEntitySubstitution("&Auml;", "A"),
				new SGMLEntitySubstitution("&auml;", "a"),
				new SGMLEntitySubstitution("&beta;", "beta"),
				new SGMLEntitySubstitution("&Cacute;", "C"),
				new SGMLEntitySubstitution("&cacute;", "c"),
				new SGMLEntitySubstitution("&Ccaron;", "C"),
				new SGMLEntitySubstitution("&ccaron;", "c"),
				new SGMLEntitySubstitution("&Ccedil;", "C"),
				new SGMLEntitySubstitution("&ccedil;", "c"),
				new SGMLEntitySubstitution("&Ccirc;", "C"),
				new SGMLEntitySubstitution("&ccirc;", "c"),
				new SGMLEntitySubstitution("&Cdot;", "C"),
				new SGMLEntitySubstitution("&cdot;", "c"),
				new SGMLEntitySubstitution("&chi;", "chi"),
				new SGMLEntitySubstitution("&copy;", "(c)"),
				new SGMLEntitySubstitution("&Dcaron;", "D"),
				new SGMLEntitySubstitution("&dcaron;", "d"),
				new SGMLEntitySubstitution("&Delta;", "Delta"),
				new SGMLEntitySubstitution("&delta;", "delta"),
				new SGMLEntitySubstitution("&Dstrok;", "D"),
				new SGMLEntitySubstitution("&dstrok;", "d"),
				new SGMLEntitySubstitution("&Eacute;", "E"),
				new SGMLEntitySubstitution("&eacute;", "e"),
				new SGMLEntitySubstitution("&Ecaron;", "E"),
				new SGMLEntitySubstitution("&ecaron;", "e"),
				new SGMLEntitySubstitution("&Ecirc;", "E"),
				new SGMLEntitySubstitution("&ecirc;", "e"),
				new SGMLEntitySubstitution("&Edot;", "E"),
				new SGMLEntitySubstitution("&edot;", "e"),
                new SGMLEntitySubstitution("&Egrave;", "E"),
	            new SGMLEntitySubstitution("&egrave;", "e"),
	            new SGMLEntitySubstitution("&Emacr;", "E"),
	            new SGMLEntitySubstitution("&emacr;", "e"),
	            new SGMLEntitySubstitution("&empty;", "empty"),
	            new SGMLEntitySubstitution("&Eogon;", "E"),
	            new SGMLEntitySubstitution("&eogon;", "e"),
                new SGMLEntitySubstitution("&epsi;", "epsilon"),
                new SGMLEntitySubstitution("&epsis;", "epsilon"), 
                new SGMLEntitySubstitution("&epsiv;", "epsilon"),                 new SGMLEntitySubstitution("&eta;", "eta"),                new SGMLEntitySubstitution("&ETH;", "TH"),                new SGMLEntitySubstitution("&eth;", "th"),                new SGMLEntitySubstitution("&Euml;", "E"),                new SGMLEntitySubstitution("&euml;", "e"),                new SGMLEntitySubstitution("&ffilig;", "ffi"),                new SGMLEntitySubstitution("&fflig;", "ff"),                new SGMLEntitySubstitution("&ffllig;", "ffl"),                new SGMLEntitySubstitution("&filig;", "fi"),                new SGMLEntitySubstitution("&fjlig;", "fj"),                new SGMLEntitySubstitution("&gacute;", "g"),                new SGMLEntitySubstitution("&Gamma;", "Gamma"),                new SGMLEntitySubstitution("&gamma;", "gamma"),                new SGMLEntitySubstitution("&Gbreve;", "G"),                new SGMLEntitySubstitution("&gbreve;", "g"),                new SGMLEntitySubstitution("&Gcedil;", "G"),                new SGMLEntitySubstitution("&Gcirc;", "G"),                new SGMLEntitySubstitution("&gcirc;", "g"),                new SGMLEntitySubstitution("&Gdot;", "G"),                new SGMLEntitySubstitution("&gdot;", "g"),                new SGMLEntitySubstitution("&half;", "1/2"),                new SGMLEntitySubstitution("&Hcirc;", "H"),                new SGMLEntitySubstitution("&hcirc;", "h"),                new SGMLEntitySubstitution("&Hstrok;", "H"),                new SGMLEntitySubstitution("&hstrok;", "h"),                new SGMLEntitySubstitution("&Icirc;", "I"),                new SGMLEntitySubstitution("&icirc;", "i"),                new SGMLEntitySubstitution("&Idot;", "I"),                new SGMLEntitySubstitution("&Igrave;", "I"),                new SGMLEntitySubstitution("&igrave;", "i"),                new SGMLEntitySubstitution("&IJlig;", "IJ"),                new SGMLEntitySubstitution("&ijlig;", "ij"),                new SGMLEntitySubstitution("&Imacr;", "I"),                new SGMLEntitySubstitution("&imacr;", "i"),                new SGMLEntitySubstitution("&infin;", "infinity"),                new SGMLEntitySubstitution("&inodot;", "i"),                new SGMLEntitySubstitution("&Iogon;", "I"),                new SGMLEntitySubstitution("&iogon;", "i"),                new SGMLEntitySubstitution("&iota;", "iota"),                new SGMLEntitySubstitution("&Itilde;", "I"),                new SGMLEntitySubstitution("&itilde;", "i"),                new SGMLEntitySubstitution("&Iuml;", "I"),                new SGMLEntitySubstitution("&iuml;", "i"),                new SGMLEntitySubstitution("&Jcirc;", "J"),                new SGMLEntitySubstitution("&jcirc;", "j"),                new SGMLEntitySubstitution("&jnodot;", "j"),                new SGMLEntitySubstitution("&kappa;", "kappa"),                new SGMLEntitySubstitution("&Kcedil;", "K"),                new SGMLEntitySubstitution("&kcedil;", "k"),                new SGMLEntitySubstitution("&Lacute;", "L"),                new SGMLEntitySubstitution("&lacute;", "l"),                new SGMLEntitySubstitution("&Lambda;", "Lambda"),                new SGMLEntitySubstitution("&lambda;", "lambda"),                new SGMLEntitySubstitution("&laquo;", "\""),                new SGMLEntitySubstitution("&Lcaron;", "L"),                new SGMLEntitySubstitution("&lcaron;", "l"),                new SGMLEntitySubstitution("&Lcedil;", "L"),                new SGMLEntitySubstitution("&lcedil;", "l"),                new SGMLEntitySubstitution("&ldquo;", "\""),                new SGMLEntitySubstitution("&ldquor;", "\""),                new SGMLEntitySubstitution("&Lmidot;", "L"),                new SGMLEntitySubstitution("&lmidot;", "l"),                new SGMLEntitySubstitution("&lowast;", "*"),                new SGMLEntitySubstitution("&lsquo;", "\'\'"),                new SGMLEntitySubstitution("&Lstrok;", "L"),                new SGMLEntitySubstitution("&lstrok;", "l"),                new SGMLEntitySubstitution("&mdash;", "---"),                new SGMLEntitySubstitution("&micro;", "micro"),                new SGMLEntitySubstitution("&mu;", "mu"),                new SGMLEntitySubstitution("&Nacute;", "N"),                new SGMLEntitySubstitution("&nacute;", "n"),                new SGMLEntitySubstitution("&Ncaron;", "N"),                new SGMLEntitySubstitution("&ncaron;", "n"),                new SGMLEntitySubstitution("&Ncedil;", "N"),                new SGMLEntitySubstitution("&ncedil;", "n"),                new SGMLEntitySubstitution("&ndash;", "--"),                new SGMLEntitySubstitution("&Ntilde;", "N"),                new SGMLEntitySubstitution("&ntilde;", "n"),                new SGMLEntitySubstitution("&nu;", "nu"),                new SGMLEntitySubstitution("&Oacute;", "O"),                new SGMLEntitySubstitution("&oacute;", "o"),                new SGMLEntitySubstitution("&Ocirc;", "O"),
                new SGMLEntitySubstitution("&ocirc;", "o"),
                new SGMLEntitySubstitution("&oacute;", "o"),
                new SGMLEntitySubstitution("&OElig;", "OE"),
                new SGMLEntitySubstitution("&oelig;", "oe"),
                new SGMLEntitySubstitution("&Ograve;", "O"),
                new SGMLEntitySubstitution("&ograve;", "o"),
                new SGMLEntitySubstitution("&Omacr;", "O"),
                new SGMLEntitySubstitution("&omacr;", "o"),
                new SGMLEntitySubstitution("&Omega;", "O"),
                new SGMLEntitySubstitution("&omega;", "o"),
                new SGMLEntitySubstitution("&Otilde;", "O"),
                new SGMLEntitySubstitution("&otilde;", "o"),
                new SGMLEntitySubstitution("&Ouml;", "O"),
                new SGMLEntitySubstitution("&ouml;", "o"),
                new SGMLEntitySubstitution("&Oacute;", "O") ,
                new SGMLEntitySubstitution("&Phi;", "phi"),
                new SGMLEntitySubstitution("&phi;", "phi"),
                new SGMLEntitySubstitution("&Phiv;", "phi"),
                new SGMLEntitySubstitution("&Pi;", "pi"),
                new SGMLEntitySubstitution("&pi;", "pi"),
                new SGMLEntitySubstitution("&piv;", "pi"),
                new SGMLEntitySubstitution("&prime;", "\'\'"),
                new SGMLEntitySubstitution("&prod;", "product"),
                new SGMLEntitySubstitution("&Psi;", "Psi"),
                new SGMLEntitySubstitution("&psi;", "psi"),
                new SGMLEntitySubstitution("&Racute;", "R"),
                new SGMLEntitySubstitution("&racute;", "r"),
                new SGMLEntitySubstitution("&raquo;", "\""),
                new SGMLEntitySubstitution("&Rcaron;", "R"),
                new SGMLEntitySubstitution("&rcaron;", "r"),
                new SGMLEntitySubstitution("&Rcedil;", "R"),
                new SGMLEntitySubstitution("&rcedil;", "r"),
                new SGMLEntitySubstitution("&rdquo;", "\""),
                new SGMLEntitySubstitution("&rdquor;", "\""),
                new SGMLEntitySubstitution("&real;", "reals"),
                new SGMLEntitySubstitution("&reg;", "RTM"),
                new SGMLEntitySubstitution("&rho;", "rho"),
                new SGMLEntitySubstitution("&rsquo;", "\'\'"),
                new SGMLEntitySubstitution("&rsquor;", "\'\'"),
                new SGMLEntitySubstitution("&Sacute;", "S"),
                new SGMLEntitySubstitution("&sacute;", "s"),
                new SGMLEntitySubstitution("&Scaron;", "S"),
                new SGMLEntitySubstitution("&scaron;", "s"),
                new SGMLEntitySubstitution("&Scedil;", "S"),
                new SGMLEntitySubstitution("&scedil;", "s"),
                new SGMLEntitySubstitution("&Scirc;", "S"),
                new SGMLEntitySubstitution("&scirc;", "s"),
                new SGMLEntitySubstitution("&Sigma;", "Sigma"),
                new SGMLEntitySubstitution("&sigma;", "sigma"),
                new SGMLEntitySubstitution("&sigmav;", "sigma"),
                new SGMLEntitySubstitution("&sum;", "sum"),
                new SGMLEntitySubstitution("&szlig;", "ss"),
                new SGMLEntitySubstitution("&tau;", "tau"),
                new SGMLEntitySubstitution("&Tcaron;", "T"),
                new SGMLEntitySubstitution("&tcaron;", "t"),
                new SGMLEntitySubstitution("&Tcedil;", "T"),
                new SGMLEntitySubstitution("&tcedil;", "t"),
                new SGMLEntitySubstitution("&Theta;", "theta"),
                new SGMLEntitySubstitution("&thetas;", "theta"),
                new SGMLEntitySubstitution("&Thinsp;", "null"),
                new SGMLEntitySubstitution("&THORN;", "TH"),
                new SGMLEntitySubstitution("&thorn;", "th"),
                new SGMLEntitySubstitution("&trade;", "(TM)"),
                new SGMLEntitySubstitution("&Tstrok;", "T"),
                new SGMLEntitySubstitution("&tstrok;", "t"),
                new SGMLEntitySubstitution("&Uacute;", "U"),
                new SGMLEntitySubstitution("&uacute;", "u"),
                new SGMLEntitySubstitution("&Ubreve;", "U"),
                new SGMLEntitySubstitution("&ubreve;", "u"),
                new SGMLEntitySubstitution("&Ucirc;", "U"),
                new SGMLEntitySubstitution("&ucirc;", "u"),
                new SGMLEntitySubstitution("&Udblac;", "U"),
                new SGMLEntitySubstitution("&udblac;", "u"),
                new SGMLEntitySubstitution("&Ugrave;", "U"),
                new SGMLEntitySubstitution("&ugrave;", "u"),
                new SGMLEntitySubstitution("&Umacr;", "U"),
                new SGMLEntitySubstitution("&umacr;", "u"),
                new SGMLEntitySubstitution("&Uogon;", "U"),
                new SGMLEntitySubstitution("&uogon;", "u"),
                new SGMLEntitySubstitution("&Upsi;", "Upsilon"),
                new SGMLEntitySubstitution("&upsi;", "upsilon"),
                new SGMLEntitySubstitution("&Utilde;", "U"),
                new SGMLEntitySubstitution("&utilde;", "u"),
                new SGMLEntitySubstitution("&Uuml;", "U"),
                new SGMLEntitySubstitution("&uuml;", "u"),
                new SGMLEntitySubstitution("&Wcirc;", "W"),
                new SGMLEntitySubstitution("&wcirc;", "w"),
                new SGMLEntitySubstitution("&Xi;", "xi"),
                new SGMLEntitySubstitution("&xi;", "xi"),
                new SGMLEntitySubstitution("&Yacute;", "Y"),
                new SGMLEntitySubstitution("&yacute;", "y"),
                new SGMLEntitySubstitution("&Yuml;", "Y"),
                new SGMLEntitySubstitution("&yuml;", "y"),
                new SGMLEntitySubstitution("&Zacute;", "Z"),
                new SGMLEntitySubstitution("&zacute;", "z"),
                new SGMLEntitySubstitution("&Zcaron;", "Z"),
                new SGMLEntitySubstitution("&zcaron;", "z"),
                new SGMLEntitySubstitution("&Zdot;", "Z"),
                new SGMLEntitySubstitution("&zdot;", "z"),
                new SGMLEntitySubstitution("&zeta;", "zeta"),
                new SGMLEntitySubstitution("&Iacute;", "I"),
                new SGMLEntitySubstitution("&amp;", "&"),
                new SGMLEntitySubstitution("&iacute;", "i"),
                new SGMLEntitySubstitution("&Oslash;", "O"),
                new SGMLEntitySubstitution("&oslash;", "o"),
                new SGMLEntitySubstitution("&nbsp;", " "),
                new SGMLEntitySubstitution("&times;", "x"),
                new SGMLEntitySubstitution("&minus;", "-"),
                new SGMLEntitySubstitution("&plusmn;", "+/-"),
                new SGMLEntitySubstitution("&thetav;", "theta"),
                new SGMLEntitySubstitution("&hellip;", "..."),
                new SGMLEntitySubstitution("\n", " "),
                new SGMLEntitySubstitution("@\\?", ""),
                new SGMLEntitySubstitution("\\*@", ""),
                new SGMLEntitySubstitution("@\\*", ""),
                new SGMLEntitySubstitution("-@", "-"),
                new SGMLEntitySubstitution("@-", "-"),
                new SGMLEntitySubstitution("@/", "/"),
                new SGMLEntitySubstitution(" @", " "),
				new SGMLEntitySubstitution("@", " ")
				);
	
	public static String applyAllTo(String x) {
		for (SGMLEntitySubstitution s : kSGMLEntitySubstitution) {
			x = s.applyTo(x);
		}
		return x;
	}
}                                           