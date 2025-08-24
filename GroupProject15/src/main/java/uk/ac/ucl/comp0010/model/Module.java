package uk.ac.ucl.comp0010.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

/**
 * Represents a module in the grade management system. This class maintains information about a
 * particular module, including its code, name and MNC (mandatory non-condonable) status.
 */
@Entity
public class Module {
  @Id
  private String code;

  private String name;
  private boolean mnc;

  @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
  private List<Grade> grades;

  /**
   * Constructs a new Module with the specified details.
   *
   * @param code The unique identifier code for the module
   * @param name The full name of the module
   * @param mnc  Whether the module is mandatory non-condonable or not
   */
  public Module(String code, String name, boolean mnc) {
    this.code = code;
    this.name = name;
    this.mnc = mnc;
  }

  /**
   * No arg constructor is required.
   */
  public Module() {
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isMnc() {
    return mnc;
  }

  public void setMnc(boolean mnc) {
    this.mnc = mnc;
  }
}
