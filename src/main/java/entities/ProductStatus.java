package entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProductStatus {
	OUT_DATE,
	INITIAL,
	AVAILABLE,
	PROCESS;
	
	@Override
	public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase().replace("_", " ");
    }
}
