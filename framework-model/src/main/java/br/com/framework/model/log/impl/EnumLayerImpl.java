package br.com.framework.model.log.impl;

public enum EnumLayerImpl {
	GLOBAL {
		@Override
		public String toString() {
			return "Global";
		}
	},
	BEAN_VALIDATION {
		@Override
		public String toString() {
			return "Bean Validation";
		}
	},
	PERSISTENCE {
		@Override
		public String toString() {
			return "Persistence";
		}
	},
	BUSINESS {
		@Override
		public String toString() {
			return "Business";
		}
	}
}
