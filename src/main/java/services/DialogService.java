package services;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;

@Stateless
public class DialogService{
	public Map<String, Object> createDialogOption(int width, int height) {
		Map<String, Object> options = new HashMap<>();
		options.put("modal", true);
		options.put("width", width);
		options.put("height", height);
		options.put("contentWidth", "100%");
		options.put("contentHeight", "100%");
		options.put("headerElement", "customheader");
		return options;
	}
}
