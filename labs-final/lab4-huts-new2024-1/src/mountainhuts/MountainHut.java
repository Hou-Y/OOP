package mountainhuts;

import java.util.Optional;

/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	private String name;
	private Integer alt;
	private String cat;
	private Integer beds;
	private Municipality mun;

	public MountainHut(String name, Integer altitude, String category, Integer bedsNumber, Municipality municipality) {
		this.name = name;
		this.alt = altitude;
		this.cat = category;
		this.beds = bedsNumber;
		this.mun = municipality;
	}

	public String getName() {
		return name;
	}

	public Optional<Integer> getAltitude() {
		/*return alt.stream()
		.toInteger()*/
		return Optional.ofNullable(alt);
	}

	public String getCategory() {
		return cat;
	}

	public Integer getBedsNumber() {
		return beds;
	}

	public Municipality getMunicipality() {
		return mun;
	}

	
}
