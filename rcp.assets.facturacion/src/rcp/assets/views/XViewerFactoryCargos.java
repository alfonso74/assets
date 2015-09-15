/*******************************************************************************
 * Copyright (c) 2004, 2007 Boeing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Boeing - initial API and implementation
 *******************************************************************************/
package rcp.assets.views;

import org.eclipse.nebula.widgets.xviewer.XViewerColumn;
import org.eclipse.nebula.widgets.xviewer.XViewerColumn.SortDataType;
import org.eclipse.nebula.widgets.xviewer.XViewerFactory;
import org.eclipse.nebula.widgets.xviewer.customize.IXViewerCustomizations;
import org.eclipse.nebula.widgets.xviewer.customize.XViewerCustomizations;
import org.eclipse.nebula.widgets.xviewer.edit.CellEditDescriptor;
import org.eclipse.nebula.widgets.xviewer.edit.ExtendedViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

/**
 * Columns for example XViewer
 * 
 * @author Donald G. Dunne
 */
public class XViewerFactoryCargos extends XViewerFactory {

   public final static String COLUMN_NAMESPACE = "xviewer.test";
   public static XViewerColumn Run_Col = new XViewerColumn(COLUMN_NAMESPACE + ".run", "Run", 80, SWT.LEFT, true,
      SortDataType.String, false, null);
   public static XViewerColumn Name_Col = new XViewerColumn(COLUMN_NAMESPACE + ".name", "Name", 200, SWT.LEFT, true,
      SortDataType.String, false, null);
   public static ExtendedViewerColumn Completed_Col = new ExtendedViewerColumn(COLUMN_NAMESPACE + ".complete", "Percent Complete",
      80, SWT.LEFT, true, SortDataType.Float, false, null);
   public static XViewerColumn Schedule_Time = new XViewerColumn(COLUMN_NAMESPACE + ".startTime", "Start Time", 40,
      SWT.CENTER, true, SortDataType.String, false, "Time this task will run");
   public static XViewerColumn Run_Db = new XViewerColumn(COLUMN_NAMESPACE + ".runDb", "Run DB", 80, SWT.LEFT, true,
      SortDataType.String, false, null);
   public static XViewerColumn Task_Type = new XViewerColumn(COLUMN_NAMESPACE + ".taskType", "Task Type", 80, SWT.LEFT,
      true, SortDataType.String, false, "This is the type of task");
   public static XViewerColumn Last_Run_Date = new XViewerColumn(COLUMN_NAMESPACE + ".lastRunDate", "Last Run", 120,
      SWT.LEFT, true, SortDataType.Date, false, "This is the last time it was run");
   public static XViewerColumn Category = new XViewerColumn(COLUMN_NAMESPACE + ".category", "Category", 80, SWT.LEFT,
      false, SortDataType.String, false, null);
   public static XViewerColumn Notification = new XViewerColumn(COLUMN_NAMESPACE + ".emailResults", "Email Results To",
      150, SWT.LEFT, true, SortDataType.String, false, "Email to send notifications to");
   public static XViewerColumn Description = new XViewerColumn(COLUMN_NAMESPACE + ".description", "Description", 300,
      SWT.LEFT, true, SortDataType.String, false, null);
   public static XViewerColumn Other_Description = new XViewerColumn(COLUMN_NAMESPACE + ".otherDescription",
      "Other Description", 75, SWT.LEFT, false, SortDataType.String, false, null);

   public XViewerFactoryCargos() {
      super("xviewer.test");
      registerColumns(Name_Col, Run_Col, Schedule_Time, Completed_Col, Run_Db, Task_Type, Last_Run_Date, Category,
         Notification, Description, Other_Description);
      
      //Completed_Col.addMapEntry(SomeTask.class, new CellEditDescriptor(Text.class, SWT.BORDER, "completed", SomeTask.class)); //$NON-NLS-1$
   }

   @Override
   public IXViewerCustomizations getXViewerCustomizations() {
      //return new MyXViewerCustomizations();
	   return new XViewerCustomizations();
   }

   @Override
   public boolean isAdmin() {
      return true;
   }

   @Override
   public boolean isCellGradientOn() {
      return true;
   }

}
