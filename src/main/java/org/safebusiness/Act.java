package org.safebusiness;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Act {
	
	@Id
	@GeneratedValue
	@Column(name="act_id")
	private Integer id;
	@OneToMany(mappedBy="act")
	private List<Section> sections;
	@Column
	private String name;
	// Procedure owning this Act
	@ManyToOne
    @JoinColumn(name="procedure_id", nullable=true)
	private Procedure procedure;
	@Column
	private String stringId;
	
	// Getters and Setters
	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Procedure getProcedure() {
		return procedure;
	}

	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	
}
