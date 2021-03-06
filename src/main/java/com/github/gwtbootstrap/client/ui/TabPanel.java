/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.gwtbootstrap.client.ui;

import com.github.gwtbootstrap.client.ui.base.DivWidget;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.resources.Bootstrap;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

//@formatter:off
/**
 * The container for a tabbable nav.
 * 
 * @since 2.0.4.0
 * @author Dominik Mayer
 * @author ohashi keisuke
 */
//@formatter:on
public class TabPanel extends DivWidget {

	private static class TabContent extends DivWidget {

		public TabContent() {
			setStyleName(Bootstrap.tab_content);
		}
	}

	static class TabLink extends NavLink {

		public TabLink(TabPane pane) {
			super(pane.getHeading());

			IconAnchor anchor = getAnchor();
			anchor.getElement().setAttribute(Constants.DATA_TOGGLE, "tab");
			anchor.getElement().setAttribute(Constants.DATA_TARGET,  "#" + pane.getId());
			if (pane.isActive()) {
				show();
			}
		}
		
		public void show() {
			setActive(true);
			show(getAnchor().getElement());
		}
		
		public native void show(Element e)/*-{
			$wnd.jQuery(e).tab('show');
		}-*/;

	}

	private NavTabs tabs = new NavTabs();

	private TabContent tabContent = new TabContent();

	public TabPanel() {
		this(Bootstrap.Tabs.ABOVE);
	}

	public TabPanel(Bootstrap.Tabs position) {
		setStyle(position);
		super.add(tabs);
		super.add(tabContent);
	}

	public void setTabPosition(String position) {
		if (position.equalsIgnoreCase("below"))
			setStyle(Bootstrap.Tabs.BELOW);
		else if (position.equalsIgnoreCase("left"))
			setStyle(Bootstrap.Tabs.LEFT);
		else if (position.equalsIgnoreCase("right"))
			setStyle(Bootstrap.Tabs.RIGHT);
		else
			setStyle(Bootstrap.Tabs.ABOVE);
	}

	@Override
	public void add(Widget child) {

		if (!(child instanceof TabPane))
			throw new IllegalArgumentException("Only Tab Panes can be added"
					+ "to a TabPanel");

		add((TabPane) child);
	}

	private void add(TabPane child) {

		TabLink tabLink = new TabLink(child);
		tabs.add(tabLink);
		tabContent.add(child);
	}
	
	@Override
	protected void onAttach() {
		super.onAttach();
		
		for(int i = 0;i < tabs.getWidgetCount();i++) {
			
			Widget widget = tabs.getWidget(i);
			
			if (widget instanceof TabLink) {
				TabLink tabLink = (TabLink) widget;
				
				if(tabLink.isActive()) {
					tabLink.show();
					break;
				}
			}
		}
	}
}
