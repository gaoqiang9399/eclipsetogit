jQuery.easing = {
	easein: function(B, C, A, E, D) {
		return E * (C /= D) * C + A
	},
	easeinout: function(B, C, A, F, E) {
		if (C < E / 2) {
			return 2 * F * C * C / (E * E) + A
		}
		var D = C - E / 2;
		return -2 * F * D * D / (E * E) + 2 * F * D / E + F / 2 + A
	},
	easeout: function(B, C, A, E, D) {
		return -E * C * C / (D * D) + 2 * E * C / D + A
	},
	expoin: function(B, C, A, F, E) {
		var D = 1;
		if (F < 0) {
			D *= -1;
			F *= -1
		}
		return D * (Math.exp(Math.log(F) / E * C)) + A
	},
	expoout: function(B, C, A, F, E) {
		var D = 1;
		if (F < 0) {
			D *= -1;
			F *= -1
		}
		return D * (-Math.exp(-Math.log(F) / E * (C - E)) + F + 1) + A
	},
	expoinout: function(B, C, A, F, E) {
		var D = 1;
		if (F < 0) {
			D *= -1;
			F *= -1
		}
		if (C < E / 2) {
			return D * (Math.exp(Math.log(F / 2) / (E / 2) * C)) + A
		}
		return D * (-Math.exp(-2 * Math.log(F / 2) / E * (C - E)) + F + 1) + A
	},
	bouncein: function(B, C, A, E, D) {
		return E - jQuery.easing.bounceout(B, D - C, 0, E, D) + A
	},
	bounceout: function(B, C, A, E, D) {
		if ((C /= D) < (1 / 2.75)) {
			return E * (7.5625 * C * C) + A
		} else {
			if (C < (2 / 2.75)) {
				return E * (7.5625 * (C -= (1.5 / 2.75)) * C + 0.75) + A
			} else {
				if (C < (2.5 / 2.75)) {
					return E * (7.5625 * (C -= (2.25 / 2.75)) * C + 0.9375) + A
				} else {
					return E * (7.5625 * (C -= (2.625 / 2.75)) * C + 0.984375) + A
				}
			}
		}
	},
	bounceinout: function(B, C, A, E, D) {
		if (C < D / 2) {
			return jQuery.easing.bouncein(B, C * 2, 0, E, D) * 0.5 + A
		}
		return jQuery.easing.bounceout(B, C * 2 - D, 0, E, D) * 0.5 + E * 0.5 + A
	},
	elasin: function(B, D, A, H, G) {
		var E = 1.70158;
		var F = 0;
		var C = H;
		if (D == 0) {
			return A
		}
		if ((D /= G) == 1) {
			return A + H
		}
		if (!F) {
			F = G * 0.3
		}
		if (C < Math.abs(H)) {
			C = H;
			E = F / 4
		} else {
			E = F / (2 * Math.PI) * Math.asin(H / C)
		}
		return -(C * Math.pow(2, 10 * (D -= 1)) * Math.sin((D * G - E) * (2 * Math.PI) / F)) + A
	},
	elasout: function(B, D, A, H, G) {
		var E = 1.70158;
		var F = 0;
		var C = H;
		if (D == 0) {
			return A
		}
		if ((D /= G) == 1) {
			return A + H
		}
		if (!F) {
			F = G * 0.3
		}
		if (C < Math.abs(H)) {
			C = H;
			E = F / 4;
		} else {
			E = F / (2 * Math.PI) * Math.asin(H / C);
		}
		return C * Math.pow(2, -10 * D) * Math.sin((D * G - E) * (2 * Math.PI) / F) + H + A
	},
	elasinout: function(B, D, A, H, G) {
		var E = 1.70158;
		var F = 0;
		var C = H;
		if (D == 0) {
			return A
		}
		if ((D /= G / 2) == 2) {
			return A + H
		}
		if (!F) {
			F = G * (0.3 * 1.5)
		}
		if (C < Math.abs(H)) {
			C = H;
			E = F / 4;
		} else {
			E = F / (2 * Math.PI) * Math.asin(H / C);
		} if (D < 1) {
			return -0.5 * (C * Math.pow(2, 10 * (D -= 1)) * Math.sin((D * G - E) * (2 * Math.PI) / F)) + A
		}
		return C * Math.pow(2, -10 * (D -= 1)) * Math.sin((D * G - E) * (2 * Math.PI) / F) * 0.5 + H + A
	},
	backin: function(B, C, A, F, E) {
		var D = 1.70158;
		return F * (C /= E) * C * ((D + 1) * C - D) + A
	},
	backout: function(B, C, A, F, E) {
		var D = 1.70158;
		return F * ((C = C / E - 1) * C * ((D + 1) * C + D) + 1) + A
	},
	backinout: function(B, C, A, F, E) {
		var D = 1.70158;
		if ((C /= E / 2) < 1) {
			return F / 2 * (C * C * (((D *= (1.525)) + 1) * C - D)) + A
		}
		return F / 2 * ((C -= 2) * C * (((D *= (1.525)) + 1) * C + D) + 2) + A
	},
	linear: function(B, C, A, E, D) {
		return E * C / D + A
	}
};