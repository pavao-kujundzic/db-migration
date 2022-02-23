/**
 * 
 */
package hr.sedamit.bss.databasemigrations.util.common;

import lombok.Data;

/**
 * Created by J0ka on 18/02/22
 *
 */
@Data
public class SqlTableColumn {
	private String name;
	private Boolean isNullable;
	private String dataType;
	private Integer ordinalPosition;
	private Integer numericScale;
	private Byte numericPrecision;
	private Integer characterMaximumLength;

	public SqlTableColumn() {

	}
}
