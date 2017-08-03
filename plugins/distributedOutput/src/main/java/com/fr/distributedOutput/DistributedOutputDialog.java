package com.fr.distributedOutput;

import com.fr.engine.local.config.DistributedPropertiesLoader;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Props;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.ColumnInfo;
import org.pentaho.di.ui.core.widget.TableView;
import org.pentaho.di.ui.trans.step.BaseStepDialog;


public class DistributedOutputDialog extends BaseStepDialog implements StepDialogInterface {
    private static final String[] COLUMN_TYPE_COMBO = ColumnTypeInstance.INSTANCE.getTypeNames();

    private int middle;
    private int margin;

    private Label wlPropertyFile;
    private Text wPropertyFile;
    private Button wbPropertyFile;
    private FormData fdlPropertyFile, fdPropertyFile, fdbPropertyFile;

    private Label wlDBName;
    private Text wDBName;
    private FormData fdlDBName, fdDBName;

    private Label wlTableName;
    private Text wTableName;
    private FormData fdlTableName, fdTableName;

    private Label wlUseTempTable;
    private Button wbUseTempTable;
    private FormData fdlUseTempTable, fdUseTempTable;

    private Label wlTempTableName;
    private Text wTempTableName;
    private FormData fdlTempTableName, fdTempTableName;

    private CTabFolder wConfigTabFolder;
    private FormData fdConfigTabFolder;

    //main tab
    private CTabItem wMainConfigTab;
    private Composite wMainConfigComp;
    private FormLayout mainConfigLayout;

    private Label wlExceptionLogFileName;
    private Button wbExceptionLogFileName;
    private Text wExceptionLogFileName;
    private FormData fdlExceptionLogFileName, fdbExceptionLogFileName, fdExceptionLogFileName;

    //table operate tab
    private CTabItem wTableOperateTab;
    private ScrolledComposite wFileSComp;
    private Composite wFileComp;

    private Label wlTabDBName;
    private Text wTabDBName;
    private FormData fdlTabDBName, fdTabDBName;

    private Label wlTabTableName;
    private Text wTabTableName;
    private FormData fdlTabTableName, fdTabTableName;

    private Label wlColumnList;
    private TableView wColumnList;
    private FormData fdlColumnList, fdColumnList;
    private FormData fdFileComp;

    private Button wbCreateTable;
    private Button wbDeleteTable;

    private DistributedOutputMeta meta;

    public DistributedOutputDialog(Shell parent, Object in, TransMeta tr, String sname) {
        super(parent, (BaseStepMeta) in, tr, sname);
        meta = (DistributedOutputMeta) in;
    }

    public String open() {
        middle = props.getMiddlePct();
        margin = Const.MARGIN;

        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MAX | SWT.MIN);
        props.setLook(shell);
        setShellImage(shell, meta);

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                meta.setChanged();
            }
        };


        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        shell.setText(Messages.getString("DistributedOutput.Shell.Title"));

        // Stepname line
        wlStepname = new Label(shell, SWT.RIGHT);
        wlStepname.setText(Messages.getString("System.Label.StepName"));
        props.setLook(wlStepname);
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment(0, 0);
        fdlStepname.right = new FormAttachment(middle, -margin);
        fdlStepname.top = new FormAttachment(0, margin);
        wlStepname.setLayoutData(fdlStepname);

        wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wStepname.setText(stepname);
        props.setLook(wStepname);
        wStepname.addModifyListener(lsMod);
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment(middle, 0);
        fdStepname.top = new FormAttachment(0, margin);
        fdStepname.right = new FormAttachment(97, 0);
        wStepname.setLayoutData(fdStepname);

        wlPropertyFile = new Label(shell, SWT.RIGHT);
        wlPropertyFile.setText(Messages.getString("DistributedOutput.Property.FileName"));
        props.setLook(wlPropertyFile);
        fdlPropertyFile = new FormData();
        fdlPropertyFile.left = new FormAttachment(0, 0);
        fdlPropertyFile.right = new FormAttachment(middle, -margin);
        fdlPropertyFile.top = new FormAttachment(wStepname, margin + 5);
        wlPropertyFile.setLayoutData(fdlPropertyFile);
        wbPropertyFile = new Button(shell, SWT.PUSH | SWT.CENTER);
        props.setLook(wbPropertyFile);
        wbPropertyFile.setText(Messages.getString("DistributedOutput.File.Search"));
        fdbPropertyFile = new FormData();
        fdbPropertyFile.right = new FormAttachment(97, 0);
        fdbPropertyFile.top = new FormAttachment(wStepname, margin + 5);
        wbPropertyFile.setLayoutData(fdbPropertyFile);
        wPropertyFile = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wPropertyFile);
        fdPropertyFile = new FormData();
        fdPropertyFile.left = new FormAttachment(middle, 0);
        fdPropertyFile.right = new FormAttachment(wbPropertyFile, -margin);
        fdPropertyFile.top = new FormAttachment(wStepname, margin + 5);
        wPropertyFile.setLayoutData(fdPropertyFile);

        wbPropertyFile.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(shell, SWT.OPEN);
                dialog.setFilterExtensions(new String[]{"*.properties", "*"});
                if (wPropertyFile.getText() != null) {
                    dialog.setFileName(wPropertyFile.getText());
                }
                dialog.setFilterNames(new String[]{
                        Messages.getString("DistributedOutput.PropertyFile.Suffix"),
                        Messages.getString("DistributedOutput.AllFile.Suffix")});
                if (dialog.open() != null) {
                    wPropertyFile.setText(dialog.getFilterPath()
                            + System.getProperty("file.separator") + dialog.getFileName());
                }
            }
        });

        wlDBName = new Label(shell, SWT.RIGHT);
        props.setLook(wlDBName);
        wlDBName.setText(Messages.getString("DistributedOutput.Label.DestDbName"));
        fdlDBName = new FormData();
        fdlDBName.left = new FormAttachment(0, 0);
        fdlDBName.right = new FormAttachment(middle, -margin);
        fdlDBName.top = new FormAttachment(wbPropertyFile, margin);
        wlDBName.setLayoutData(fdlDBName);
        wDBName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdDBName = new FormData();
        fdDBName.left = new FormAttachment(middle, 0);
        fdDBName.right = new FormAttachment(97, 0);
        fdDBName.top = new FormAttachment(wbPropertyFile, margin);
        wDBName.setLayoutData(fdDBName);

        wlTableName = new Label(shell, SWT.RIGHT);
        props.setLook(wlTableName);
        wlTableName.setText(Messages.getString("DistributedOutput.Label.DestTableName"));
        fdlTableName = new FormData();
        fdlTableName.left = new FormAttachment(0, 0);
        fdlTableName.right = new FormAttachment(middle, -margin);
        fdlTableName.top = new FormAttachment(wDBName, margin);
        wlTableName.setLayoutData(fdlTableName);
        wTableName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdTableName = new FormData();
        fdTableName.left = new FormAttachment(middle, 0);
        fdTableName.right = new FormAttachment(97, 0);
        fdTableName.top = new FormAttachment(wDBName, margin);
        wTableName.setLayoutData(fdTableName);

        wlUseTempTable = new Label(shell, SWT.RIGHT);
        wlUseTempTable.setText(Messages.getString("DistributedOutput.Label.UseTempTable"));
        props.setLook(wlUseTempTable);
        fdlUseTempTable = new FormData();
        fdlUseTempTable.left = new FormAttachment(0, 0);
        fdlUseTempTable.right = new FormAttachment(middle, -margin);
        fdlUseTempTable.top = new FormAttachment(wTableName, margin);
        wlUseTempTable.setLayoutData(fdlUseTempTable);
        wbUseTempTable = new Button(shell, SWT.CHECK);
        props.setLook(wbUseTempTable);
        fdUseTempTable = new FormData();
        fdUseTempTable.left = new FormAttachment(middle, 0);
        fdUseTempTable.right = new FormAttachment(97, 0);
        fdUseTempTable.top = new FormAttachment(wTableName, margin);
        wbUseTempTable.setLayoutData(fdUseTempTable);

        wlTempTableName = new Label(shell, SWT.RIGHT);
        wlTempTableName.setText(Messages.getString("DistributedOutput.Label.TempTableName"));
        props.setLook(wlTempTableName);
        fdlTempTableName = new FormData();
        fdlTempTableName.left = new FormAttachment(0, 0);
        fdlTempTableName.right = new FormAttachment(middle, -margin);
        fdlTempTableName.top = new FormAttachment(wbUseTempTable, margin);
        wlTempTableName.setLayoutData(fdlTempTableName);
        wTempTableName = new Text(shell, SWT.SINGLE | SWT.BORDER | SWT.LEFT);
        props.setLook(wTempTableName);
        fdTempTableName = new FormData();
        fdTempTableName.left = new FormAttachment(middle, 0);
        fdTempTableName.right = new FormAttachment(97, 0);
        fdTempTableName.top = new FormAttachment(wbUseTempTable, margin);
        wTempTableName.setLayoutData(fdTempTableName);

        wConfigTabFolder = new CTabFolder(shell, SWT.BORDER);
        props.setLook(wConfigTabFolder, Props.WIDGET_STYLE_TAB);
        wConfigTabFolder.setSimple(false);

        addMainTab();
        addTableOperateTab();

        fdConfigTabFolder = new FormData();
        fdConfigTabFolder.left = new FormAttachment(0, 0);
        fdConfigTabFolder.right = new FormAttachment(97, 0);
        fdConfigTabFolder.top = new FormAttachment(wTempTableName, margin);
        fdConfigTabFolder.bottom = new FormAttachment(100, -50);
        wConfigTabFolder.setLayoutData(fdConfigTabFolder);
        wConfigTabFolder.setSelection(0);

        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(Messages.getString("System.Button.OK"));
        wCancel = new Button(shell, SWT.PUSH);
        wCancel.setText(Messages.getString("System.Button.Cancel"));

        setButtonPositions(new Button[]{wOK, wCancel}, margin, null);

        // Add listeners
        lsOK = e -> ok();

        lsCancel = e -> cancel();

        wOK.addListener(SWT.Selection, lsOK);
        wCancel.addListener(SWT.Selection, lsCancel);


        shell.addShellListener(new ShellAdapter() {
            public void shellClosed(ShellEvent e) {
                cancel();
            }
        });


        getData();

        setSize();

        meta.setChanged(true);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        return stepname;
    }

    private void addMainTab() {
        wMainConfigTab = new CTabItem(wConfigTabFolder, SWT.None);
        wMainConfigTab.setText(Messages.getString("DistributedOutput.Label.MainTab"));

        wMainConfigComp = new Composite(wConfigTabFolder, SWT.NONE);
        props.setLook(wMainConfigComp);

        mainConfigLayout = new FormLayout();
        mainConfigLayout.marginWidth = 30;
        mainConfigLayout.marginHeight = 30;
        wMainConfigComp.setLayout(mainConfigLayout);

        wlExceptionLogFileName = new Label(wMainConfigComp, SWT.RIGHT);
        wlExceptionLogFileName.setText(Messages.getString("DistributedOutput.Label.ExceptionFileName"));
        props.setLook(wlExceptionLogFileName);
        fdlExceptionLogFileName = new FormData();
        fdlExceptionLogFileName.left = new FormAttachment(0, 0);
        fdlExceptionLogFileName.right = new FormAttachment(middle, -margin);
        fdlExceptionLogFileName.top = new FormAttachment(0, margin + 5);
        wlExceptionLogFileName.setLayoutData(fdlExceptionLogFileName);
        wbExceptionLogFileName = new Button(wMainConfigComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbExceptionLogFileName);
        wbExceptionLogFileName.setText(Messages.getString("DistributedOutput.File.Search"));
        fdbExceptionLogFileName = new FormData();
        fdbExceptionLogFileName.right = new FormAttachment(97, 0);
        fdbExceptionLogFileName.top = new FormAttachment(0, margin + 5);
        wbExceptionLogFileName.setLayoutData(fdbExceptionLogFileName);
        wExceptionLogFileName = new Text(wMainConfigComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        props.setLook(wExceptionLogFileName);
        fdExceptionLogFileName = new FormData();
        fdExceptionLogFileName.left = new FormAttachment(middle, 0);
        fdExceptionLogFileName.right = new FormAttachment(wbExceptionLogFileName, -margin);
        fdExceptionLogFileName.top = new FormAttachment(0, margin + 5);
        wExceptionLogFileName.setLayoutData(fdExceptionLogFileName);

        wbExceptionLogFileName.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(shell, SWT.OPEN);
                dialog.setFilterExtensions(new String[]{"*.txt", "*"});
                if (wExceptionLogFileName.getText() != null) {
                    dialog.setFileName(wExceptionLogFileName.getText());
                }
                dialog.setFilterNames(new String[]{
                        Messages.getString("DistributedOutput.Txt.Suffix"),
                        Messages.getString("DistributedOutput.AllFile.Suffix")});
                if (dialog.open() != null) {
                    wExceptionLogFileName.setText(dialog.getFilterPath()
                            + System.getProperty("file.separator") + dialog.getFileName());
                }
            }
        });
//        wMainConfigComp.layout();
        wMainConfigTab.setControl(wMainConfigComp);
    }

    private void addTableOperateTab() {
        wTableOperateTab = new CTabItem(wConfigTabFolder, SWT.None);
        wTableOperateTab.setText(Messages.getString("DistributedOutput.Label.TableOperateTab"));

        wFileSComp = new ScrolledComposite(wConfigTabFolder, SWT.V_SCROLL | SWT.H_SCROLL);
        wFileSComp.setLayout(new FillLayout());

        wFileComp = new Composite(wFileSComp, SWT.NONE);
        props.setLook(wFileComp);


        FormLayout fileLayout = new FormLayout();
        fileLayout.marginWidth = 3;
        fileLayout.marginHeight = 3;
        wFileComp.setLayout(fileLayout);

        wlTabDBName = new Label(wFileComp, SWT.RIGHT);
        wlTabDBName.setText(Messages.getString("DistributedOutput.Label.DbName"));
        props.setLook(wlTabDBName);
        fdlTabDBName = new FormData();
        fdlTabDBName.left = new FormAttachment(0, 0);
        fdlTabDBName.right = new FormAttachment(middle, -margin);
        fdlTabDBName.top = new FormAttachment(0, margin);
        wlTabDBName.setLayoutData(fdlTabDBName);
        wTabDBName = new Text(wFileComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdTabDBName = new FormData();
        fdTabDBName.left = new FormAttachment(middle, 0);
        fdTabDBName.right = new FormAttachment(97, 0);
        fdTabDBName.top = new FormAttachment(0, margin);
        wTabDBName.setLayoutData(fdTabDBName);

        wlTabTableName = new Label(wFileComp, SWT.RIGHT);
        wlTabTableName.setText(Messages.getString("DistributedOutput.Label.TableName"));
        props.setLook(wlTabTableName);
        fdlTabTableName = new FormData();
        fdlTabTableName.left = new FormAttachment(0, 0);
        fdlTabTableName.right = new FormAttachment(middle, -margin);
        fdlTabTableName.top = new FormAttachment(wTabDBName, margin);
        wlTabTableName.setLayoutData(fdlTabTableName);
        wTabTableName = new Text(wFileComp, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdTabTableName = new FormData();
        fdTabTableName.left = new FormAttachment(middle, 0);
        fdTabTableName.right = new FormAttachment(97, 0);
        fdTabTableName.top = new FormAttachment(wTabDBName, margin);
        wTabTableName.setLayoutData(fdTabTableName);

        ColumnInfo[] colinfo =
                new ColumnInfo[]{new ColumnInfo(Messages.getString("DistributedOutput.Column.Name"), ColumnInfo.COLUMN_TYPE_TEXT, false),
                        new ColumnInfo(Messages.getString("DistributedOutput.Column.Type"), ColumnInfo.COLUMN_TYPE_CCOMBO, COLUMN_TYPE_COMBO),
                        new ColumnInfo(Messages.getString("DistributedOutput.Column.Other"), ColumnInfo.COLUMN_TYPE_TEXT, false)};

        colinfo[0].setUsingVariables(true);
//        colinfo[0].setToolTip(Messages.getString("TextFileInputDialog.RegExpColumn.Column"));

        ModifyListener lsMod = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                meta.setChanged();
            }
        };

        wlColumnList = new Label(wFileComp, SWT.RIGHT);
        wlColumnList.setText(Messages.getString("DistributedOutput.Label.Column"));
        props.setLook(wlColumnList);
        fdlColumnList = new FormData();
        fdlColumnList.left = new FormAttachment(0, 0);
        fdlColumnList.right = new FormAttachment(middle, -margin);
        fdlColumnList.top = new FormAttachment(wTabTableName, margin);
        wlColumnList.setLayoutData(fdlColumnList);
        wColumnList = new TableView(transMeta, wFileComp, SWT.FULL_SELECTION | SWT.SINGLE | SWT.BORDER, colinfo, 4, lsMod, props);
        props.setLook(wColumnList);
        fdColumnList = new FormData();
        fdColumnList.left = new FormAttachment(middle, 0);
        fdColumnList.right = new FormAttachment(97, 0);
        fdColumnList.top = new FormAttachment(wTabTableName, margin);
        wColumnList.setLayoutData(fdColumnList);

        wbCreateTable = new Button(wFileComp, SWT.PUSH);
        wbCreateTable.setText(Messages.getString("DistributedOutput.Table.Create"));
        wbDeleteTable = new Button(wFileComp, SWT.PUSH);
        wbDeleteTable.setText(Messages.getString("DistributedOutput.Table.Delete"));

        setButtonPositions(new Button[]{wbCreateTable, wbDeleteTable}, margin, wColumnList);

        Listener lsCreateTable = e -> createTable();
        wbCreateTable.addListener(SWT.SELECTED, lsCreateTable);

        Listener lsDeleteTable = e -> deleteTable();
        wbDeleteTable.addListener(SWT.SELECTED, lsDeleteTable);

        fdFileComp = new FormData();
        fdFileComp.left = new FormAttachment(0, 0);
        fdFileComp.top = new FormAttachment(wTableName, 0);
        fdFileComp.right = new FormAttachment(100, 0);
        fdFileComp.bottom = new FormAttachment(100, 0);
        wFileComp.setLayoutData(fdFileComp);

        wFileComp.pack();
        Rectangle bounds = wFileComp.getBounds();

        wFileSComp.setContent(wFileComp);
        wFileSComp.setExpandHorizontal(true);
        wFileSComp.setExpandVertical(true);
        wFileSComp.setMinWidth(bounds.width);
        wFileSComp.setMinHeight(bounds.height);

        wTableOperateTab.setControl(wFileSComp);
    }

    /**
     * Copy information from the meta-data meta to the dialog fields.
     */
    public void getData() {
        if (!StringUtils.isEmpty(meta.getDbName())) {
            wDBName.setText(meta.getDbName());
        }
        if (!StringUtils.isEmpty(meta.getTableName())) {
            wTableName.setText(meta.getTableName());
        }
        if (!StringUtils.isEmpty(meta.getPropertyFileName())) {
            wPropertyFile.setText(meta.getPropertyFileName());
        }
        if (!StringUtils.isEmpty(meta.getTempTableName())) {
            wTempTableName.setText(meta.getTempTableName());
        }
        wbUseTempTable.setSelection(meta.isUseTempTable());
    }

    private void cancel() {
        //设置之后setsize才能生效
        meta.setChanged(true);
        dispose();
    }


    private void ok() {
        // Get the information for the dialog into the meta structure.
        meta.setDbName(wDBName.getText());
        meta.setTableName(wTableName.getText());
        meta.setPropertyFileName(wPropertyFile.getText());
        meta.setTempTableName(wTempTableName.getText());
        meta.setUseTempTable(wbUseTempTable.getSelection());
        dispose();
    }

    private void deleteTable() {
        MessageBox box = new MessageBox(shell, SWT.NONE);
        String dbName = wTabDBName.getText();
        String tableName = wTabTableName.getText();
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            box.setMessage("dbName or tableName can't be null");
            box.open();
            return;
        }
        try {
            TableUtil.deleteTable(dbName, tableName);
            box.setMessage("success");
            box.open();
            return;
        } catch (Exception e) {
            box.setMessage("fail");
            box.open();
            return;
        }
    }

    private void createTable() {
        MessageBox box = new MessageBox(shell, SWT.NONE);
        String propertyFileName = wPropertyFile.getText();
        DistributedPropertiesLoader.load(propertyFileName);
        String[] columnNames = wColumnList.getItems(0);
        String[] columnTypes = wColumnList.getItems(1);
        String dbName = wTabDBName.getText();
        String tableName = wTabTableName.getText();
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            box.setMessage("dbName or tableName can't be null");
            box.open();
            return;
        }
        try {
            TableUtil.createTable(dbName, tableName, columnNames, columnTypes);
            box.setMessage("success");
            box.open();
            return;
        } catch (Exception e) {
            box.setMessage("fail");
            box.open();
            return;
        }
    }

    public String toString() {
        return this.getClass().getName();
    }
}
