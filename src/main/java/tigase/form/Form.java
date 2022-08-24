/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.form;

import tigase.xml.Element;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p> Created: 2007-05-27 11:41:02 </p>
 *
 * @author bmalkow
 */
public class Form
		extends AbstractForm {

	protected static final Logger log = Logger.getLogger(Form.class.getName());
	protected Fields fields = new Fields();

	public Form(Element form) {

		super(form);

		List<Element> children = form.getChildren();

		if (children != null) {
			for (Element sub : children) {
				if ("field".equals(sub.getName())) {
					Field field = new Field(sub);

					if (log.isLoggable(Level.FINEST)) {
						log.finest("read Data Form field [" + field.getVar() + "]");
					}
					fields.addField(field);
				}
			}
		}
	}

	public Form(String type, String title, String instruction) {
		super(type, title, instruction);
	}

	public void addField(final Field field) {
		fields.addField(field);
	}

	public void clear() {
		fields.clear();
	}

	public void copyValuesFrom(Element form) {
		log.finest("Copying values from form ");

		List<Element> children = form.getChildren();

		if (children != null) {
			for (Element sub : children) {
				if ("field".equals(sub.getName())) {
					Field field = new Field(sub);
					Field f = fields.get(field.getVar());

					if (f != null) {
						f.setValues(field.getValues());
					} else if (log.isLoggable(Level.FINER)) {
						log.finer("Field " + field.getVar() + " is not declared in form '" + title + "'!");
					}
				}
			}
		}
	}

	public void copyValuesFrom(Form form) {
		for (Field field : form.fields.getAllFields()) {
			Field f = fields.get(field.getVar());

			if (f != null) {
				f.setValues(field.getValues());
			} else if (log.isLoggable(Level.FINER)) {
				log.finer("Field " + field.getVar() + " is not declared in form '" + title + "'!");
			}
		}
	}

	public Field get(String var) {
		return this.fields.get(var);
	}

	public List<Field> getAllFields() {
		return this.fields.getAllFields();
	}

	public Boolean getAsBoolean(String var) {
		return fields.getAsBoolean(var);
	}

	public Integer getAsInteger(String var) {
		return fields.getAsInteger(var);
	}

	public Long getAsLong(String var) {
		return fields.getAsLong(var);
	}

	public String getAsString(String var) {
		return fields.getAsString(var);
	}

	public String[] getAsStrings(String var) {
		return fields.getAsStrings(var);
	}

	@Override
	public Element getElement() {
		Element form = super.getElement();

		for (Field field : this.fields.getAllFields()) {
			form.addChild(field.getElement());
		}

		return form;
	}

	@Override
	public String getInstruction() {
		return instruction;
	}

	@Override
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getTypeOrElse(String defValue) {
		if (type == null) {
			return defValue;
		}
		return type;
	}

	public boolean is(String var) {
		return this.fields.is(var);
	}

	@Override
	public boolean isType(String type) {
		return this.type != null && this.type.equals(type);
	}

	public void removeField(final String var) {
		fields.removeField(var);
	}

	@Override
	public String toString() {
		return "Form{" + "fields=" + fields + '}';
	}
}
