package org.safebusiness;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.safebusiness.api.repo.ActionAttributeRepository;
import org.safebusiness.api.repo.ProcedureTemplateRepository;

/**
 * This a dynamically generated <code>Law</code> document whose
 * structure is determined by the associated {@link Process}
 * 
 * @author samuel
 *
 */
@Entity
public class Document {

	@Id
	@GeneratedValue
	@Column
	private Integer id;
	@Column
	private String name;
	@ManyToOne
	@JoinColumn(name="process_id")
	private Process process;
	@OneToMany(mappedBy="document", fetch=FetchType.EAGER)
	private List<ProcedureTemplate> templates;
	@Column
	private String processName;
	@Column
	private String clientFirstName;
	@Column
	private String clientLastName;
	
	public Document() {
		
	}
	
	public Document(Process process) {
		//setupTemplates(process);
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public List<ProcedureTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<ProcedureTemplate> templates) {
		this.templates = templates;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public String getClientLastName() {
		return clientLastName;
	}

	public void setClientLastName(String clientLastName) {
		this.clientLastName = clientLastName;
	}

	public void setupTemplates(Process process, ActionAttributeRepository attRepo, ProcedureTemplateRepository templateRepo) {
		this.process = process;
		if (templates == null) {
			templates = new ArrayList<>();
		}
		List<Procedure> procedures = process.getProcedures();
		if (procedures == null) {
			throw new IllegalArgumentException("Can't create Document from an Empty Process."
					+ " No Procedures found!");
		}
		for (Procedure procedure : procedures) {
			ProcedureTemplate template = new ProcedureTemplate();
			List<Act> acts = procedure.getActs();
			if (acts != null) {
				template.setActs(acts);
			}
			if (procedure.getAction() == null) {
				throw new IllegalArgumentException("Can't use Procedure with undefined Action");
			}
			template.setInstructions(procedure.getAction().getInstructionString());
			// First save the template
			templateRepo.save(template);
			List<AttributeType> types = procedure.getAction().getAttributeTypes();
			if (types != null) {
				for (AttributeType type : types) {
					ActionAttribute attribute = new ActionAttribute();
					attribute.setName(type.getName());
					attribute.setDataTypeString(type.getDataTypeString());
					template.getAttributes().add(attribute);
				}
			}
			templates.add(template);
		}

	}
	
	public void populateMetadata() {
		for (ProcedureTemplate template : templates) {
			//template.
		}
	}
	
}
