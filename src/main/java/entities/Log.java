package entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="log")
@NamedQuery(name = "findAllLog", query = "SELECT l FROM Log l order by l.id desc")
@Data
@Builder
public class Log {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="action")
	private Action action;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="log_time")
	private Date logTime;
	
	@Column(name="note")
	private String note;
	
}
