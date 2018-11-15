package com.baoviet.agency.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.baoviet.agency.dto.momo.component.ItemOptionComponent;
import com.baoviet.agency.dto.momo.component.MomoComponent;
import com.baoviet.agency.dto.momo.component.StyleComponent;

public class ComponentUtils {

	public static void setComponentErrorMessage(List<MomoComponent> form, String id, String errorMessage) {
		for (MomoComponent item : form) {
			if (StringUtils.equals(item.getId(), id)) {
				item.setErrorMessage(errorMessage);
				return;
			}
		}
	}
	
	// Value : String / ArrayList
	public static Object getComponentValue(List<MomoComponent> form, String id) {
		for (MomoComponent item : form) {
			if (StringUtils.equals(item.getId(), id)) {
				return item.getValue();
			}
		}
		
		return null;
	}
	
	public static void createLabel(List<MomoComponent> form, String id, String value, String style) {
		// Label
		MomoComponent lblInfo = new MomoComponent();
		lblInfo.setId(id);
		lblInfo.setValue(value);
		lblInfo.setType(MomoComponent.COMPONENT_TYPE_TEXT_VIEW);
		if (style.equals("1")) {
			lblInfo.setStyle(new StyleComponent("bold", 15, 15, 17, "#4D4D4D"));	
		}
		if (style.equals("2")) {
			StyleComponent styleTuNgay = new StyleComponent();
			styleTuNgay.setPaddingBottom(20);
			lblInfo.setStyle(styleTuNgay);	
		}
		form.add(lblInfo);		
	}
	
	public static void createTextInputAutoFill(List<MomoComponent> form, String id, String autofillName, String placeholder, String title, String validateRegex, String value, String keyboardType) {
		// textbox
		MomoComponent textInput = new MomoComponent();
		textInput.setId(id);
		textInput.setAutoFill(autofillName);
		textInput.setType(MomoComponent.COMPONENT_TYPE_TEXT_INPUT);
		if (!StringUtils.isEmpty(placeholder)) {
			textInput.getProperties().put("placeholder", placeholder);
		}
		if (!StringUtils.isEmpty(title)) {
			textInput.getProperties().put("title", title);
		}
		if (!StringUtils.isEmpty(validateRegex)) {
			textInput.getProperties().put("validateRegex", validateRegex);
		}
		if (!StringUtils.isEmpty(value)) {
			textInput.setValue(value);
		}
		if (!StringUtils.isEmpty(keyboardType)) {
			textInput.getProperties().put("keyboardType", "numeric");
			textInput.getProperties().put("filterCharRegex", "/([0-9])/i");
		}
		
		form.add(textInput);	
	}
	
	public static void createTextInput(List<MomoComponent> form, String id, String placeholder, String title, String validateRegex, String keyboardType) {
		// textbox
		MomoComponent textInput = new MomoComponent();
		textInput.setId(id);
		textInput.setType(MomoComponent.COMPONENT_TYPE_TEXT_INPUT);
		if (!StringUtils.isEmpty(placeholder)) {
			textInput.getProperties().put("placeholder", placeholder);
		}
		if (!StringUtils.isEmpty(title)) {
			textInput.getProperties().put("title", title);
		}
		if (!StringUtils.isEmpty(validateRegex)) {
			textInput.getProperties().put("validateRegex", validateRegex);
		}
		if (!StringUtils.isEmpty(keyboardType)) {
			textInput.getProperties().put("keyboardType", "numeric");
			textInput.getProperties().put("filterCharRegex", "/([0-9])/i");
		}
		
		form.add(textInput);	
	}
	
	public static void createDateInput(List<MomoComponent> form, String id, String placeholder, String inputFormat, String outputFormat) {
		// textbox
		MomoComponent dateInput = new MomoComponent();
		dateInput.setId(id);
		dateInput.setType(MomoComponent.COMPONENT_TYPE_CALENDAR_PICKER);
		if (!StringUtils.isEmpty(placeholder)) {
			dateInput.getProperties().put("placeholder", placeholder);
		}
		if (!StringUtils.isEmpty(inputFormat)) {
			dateInput.getProperties().put("inputFormat", inputFormat);
		}
		if (!StringUtils.isEmpty(outputFormat)) {
			dateInput.getProperties().put("outputFormat", outputFormat);
		}
		String strDay = DateUtils.getCurrentYearAfter1Day();
		dateInput.setValue(strDay);
		
		form.add(dateInput);	
	}
	
	public static void createDateInputVatChat(List<MomoComponent> form, String id, String placeholder, String inputFormat, String outputFormat) {
		// textbox
		MomoComponent dateInput = new MomoComponent();
		dateInput.setId(id);
		dateInput.setType(MomoComponent.COMPONENT_TYPE_CALENDAR_PICKER);
		if (!StringUtils.isEmpty(placeholder)) {
			dateInput.getProperties().put("placeholder", placeholder);
		}
		if (!StringUtils.isEmpty(inputFormat)) {
			dateInput.getProperties().put("inputFormat", inputFormat);
		}
		if (!StringUtils.isEmpty(outputFormat)) {
			dateInput.getProperties().put("outputFormat", outputFormat);
		}
		
		Date date = DateUtils.getCurrentYearPrevious(new Date(), 4);
		dateInput.setValue(DateUtils.date2Str(date));
		
		form.add(dateInput);	
	}
	
	public static void createCheckBoxInput(List<MomoComponent> form, String id, String text, String checkBBDS) {
		// textbox
		MomoComponent checkBoxInput = new MomoComponent();
		checkBoxInput.setId(id);
		checkBoxInput.setType(MomoComponent.COMPONENT_TYPE_CHECK_BOX);
		if (StringUtils.equals(checkBBDS, "1")) {
			checkBoxInput.setValue(true);
		} else {
			checkBoxInput.setValue(false);	
		}
		if (!StringUtils.isEmpty(text)) {
			checkBoxInput.setText(text);
		}
		form.add(checkBoxInput);	
	}
	
	public static void createButton(List<MomoComponent> form, String id, String text, String title, List<String> bindId) {
		// textbox
		MomoComponent button = new MomoComponent();
		button.setId(id);
		button.setType(MomoComponent.COMPONENT_TYPE_BUTTON);
		if (!StringUtils.isEmpty(text)) {
			button.setText(text);
		}
		if (!StringUtils.isEmpty(title)) {
			button.setTitle(title);
		}
		if (bindId != null && bindId.size() > 0) {
			button.setBindId(bindId);	
		}

		form.add(button);	
	}
	
	public static void createComboboxInput(List<MomoComponent> form, String id, String placeholder, List<ItemOptionComponent> items) {
		// combobox
		MomoComponent comboboxInput = new MomoComponent();
		comboboxInput.setId(id);
		comboboxInput.setType(MomoComponent.COMPONENT_TYPE_LIST_SELECTOR);
		comboboxInput.getProperties().put("placeholder", placeholder);
		comboboxInput.setItems(items);
		
		form.add(comboboxInput);	
	}
	
	public static void createCheckListInput(List<MomoComponent> form, String id, String title, List<ItemOptionComponent> items, String requireItemCount, List<Object> requireItemValues) {
		// checklist
		MomoComponent checkListInput = new MomoComponent();
		checkListInput.setId(id);
		checkListInput.setType(MomoComponent.COMPONENT_TYPE_CHECK_LIST);
		checkListInput.getProperties().put("title", title);
		checkListInput.getProperties().put("itemDirection", "column");
		if (!StringUtils.isEmpty(requireItemCount)) {
			checkListInput.getProperties().put("requireItemCount", requireItemCount);
		}
		if (requireItemValues != null && requireItemValues.size() > 0) {
			checkListInput.getProperties().put("requireItemValues", requireItemValues);
		}
		checkListInput.setItems(items);
		
		form.add(checkListInput);	
	}
	
	public static void createRadioInput(List<MomoComponent> form, String id, String title, List<ItemOptionComponent> items) {
		// radio
		MomoComponent radioInput = new MomoComponent();
		radioInput.setId(id);
		radioInput.setType(MomoComponent.COMPONENT_TYPE_RADIO_GROUP);
		radioInput.getProperties().put("title", title);
		radioInput.setItems(items);
		
		form.add(radioInput);	
	}
	
	public static void createStepIndicator(List<MomoComponent> form, String id, String value) {
		// step
		List<ItemOptionComponent> lstStep = new ArrayList<>();
		ItemOptionComponent step1 = new ItemOptionComponent();
		step1.setText("Tính phí");
		ItemOptionComponent step2 = new ItemOptionComponent();
		step2.setText("Thông tin bảo hiểm");
		ItemOptionComponent step3 = new ItemOptionComponent();
		step3.setText("Thông tin giao hàng");
		ItemOptionComponent step4 = new ItemOptionComponent();
		step4.setText("Tóm tắt đơn hàng");
		lstStep.add(step1);
		lstStep.add(step2);
		lstStep.add(step3);
		lstStep.add(step4);
		
		MomoComponent stepInput = new MomoComponent();
		stepInput.setId(id);
		stepInput.setType(MomoComponent.COMPONENT_STEP_INDICATOR);
		stepInput.setValue(value);
		stepInput.setItems(lstStep);
		form.add(stepInput);	
	}
	
	public static List<List<ItemOptionComponent>> createRowTable(String text1, String text2, String checkStyle2) {
		// radio
		List<List<ItemOptionComponent>> rows = new ArrayList<>();
		List<ItemOptionComponent> lstrows = new ArrayList<>();
		ItemOptionComponent row1c1 = new ItemOptionComponent();
		row1c1.setText(text1);
		row1c1.setStyle(new StyleComponent(1,"#8F8E94"));
		ItemOptionComponent row1c2 = new ItemOptionComponent();
		row1c2.setText(text2);
		if(StringUtils.equals(checkStyle2, "1")) {
			row1c2.setStyle(new StyleComponent("bold", "#4D4D4D", 2,"right"));
		} else if (StringUtils.equals(checkStyle2, "2")) {
			row1c2.setStyle(new StyleComponent("bold", 17, "#4D4D4D", 2,"right"));
		} else {
			StyleComponent style = new StyleComponent();
			style.setFlex(2);
			style.setTextAlign("right");
			row1c2.setStyle(style);	
		}
		lstrows.add(row1c1);
		lstrows.add(row1c2);
		rows.add(lstrows);
		return rows;
	}
	
}

