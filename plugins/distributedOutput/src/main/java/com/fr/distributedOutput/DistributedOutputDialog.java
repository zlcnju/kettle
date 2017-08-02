package com.fr.distributedOutput;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Props;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;


public class DistributedOutputDialog extends BaseStepDialog implements StepDialogInterface {
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
    private Composite wTableOperateComp;

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
        fdStepname.right = new FormAttachment(100, 0);
        wStepname.setLayoutData(fdStepname);

        wlPropertyFile = new Label(shell, SWT.RIGHT);
        wlPropertyFile.setText(Messages.getString("property fileName"));
        props.setLook(wlPropertyFile);
        fdlPropertyFile = new FormData();
        fdlPropertyFile.left = new FormAttachment(0, 0);
        fdlPropertyFile.right = new FormAttachment(middle, -margin);
        fdlPropertyFile.top = new FormAttachment(wStepname, margin + 5);
        wlPropertyFile.setLayoutData(fdlPropertyFile);
        wbPropertyFile = new Button(shell, SWT.PUSH | SWT.CENTER);
        props.setLook(wbPropertyFile);
        wbPropertyFile.setText(Messages.getString("browse"));
        fdbPropertyFile = new FormData();
        fdbPropertyFile.right = new FormAttachment(100, 0);
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
                        Messages.getString("*.properties"),
                        Messages.getString("所有文件")});
                if (dialog.open() != null) {
                    wPropertyFile.setText(dialog.getFilterPath()
                            + System.getProperty("file.separator") + dialog.getFileName());
                }
            }
        });

        wlDBName = new Label(shell, SWT.RIGHT);
        props.setLook(wlDBName);
        wlDBName.setText("dest DB name");
        fdlDBName = new FormData();
        fdlDBName.left = new FormAttachment(0, 0);
        fdlDBName.right = new FormAttachment(middle, -margin);
        fdlDBName.top = new FormAttachment(wbPropertyFile, margin);
        wlDBName.setLayoutData(fdlDBName);
        wDBName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdDBName = new FormData();
        fdDBName.left = new FormAttachment(middle, 0);
        fdDBName.right = new FormAttachment(100, 0);
        fdDBName.top = new FormAttachment(wbPropertyFile, margin);
        wDBName.setLayoutData(fdDBName);

        wlTableName = new Label(shell, SWT.RIGHT);
        props.setLook(wlTableName);
        wlTableName.setText("dest tableName name");
        fdlTableName = new FormData();
        fdlTableName.left = new FormAttachment(0, 0);
        fdlTableName.right = new FormAttachment(middle, -margin);
        fdlTableName.top = new FormAttachment(wDBName, margin);
        wlTableName.setLayoutData(fdlTableName);
        wTableName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        fdTableName = new FormData();
        fdTableName.left = new FormAttachment(middle, 0);
        fdTableName.right = new FormAttachment(100, 0);
        fdTableName.top = new FormAttachment(wDBName, margin);
        wTableName.setLayoutData(fdTableName);

        wlUseTempTable = new Label(shell, SWT.RIGHT);
        wlUseTempTable.setText(Messages.getString("use temp table to full load?"));
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
        wlTempTableName.setText(Messages.getString("temp table name"));
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

        fdConfigTabFolder = new FormData();
        fdConfigTabFolder.left = new FormAttachment(0, 0);
        fdConfigTabFolder.right = new FormAttachment(100, 0);
        fdConfigTabFolder.top = new FormAttachment(wTempTableName, margin);
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
        wMainConfigTab.setText("主选项");

        wMainConfigComp = new Composite(wConfigTabFolder, SWT.NONE);
        props.setLook(wMainConfigComp);

        mainConfigLayout = new FormLayout();
        mainConfigLayout.marginWidth = 30;
        mainConfigLayout.marginHeight = 30;
        wMainConfigComp.setLayout(mainConfigLayout);

        wlExceptionLogFileName = new Label(wMainConfigComp, SWT.RIGHT);
        wlExceptionLogFileName.setText(Messages.getString("exception log fileName"));
        props.setLook(wlExceptionLogFileName);
        fdlExceptionLogFileName = new FormData();
        fdlExceptionLogFileName.left = new FormAttachment(0, 0);
        fdlExceptionLogFileName.right = new FormAttachment(middle, -margin);
        fdlExceptionLogFileName.top = new FormAttachment(0, margin + 5);
        wlExceptionLogFileName.setLayoutData(fdlExceptionLogFileName);
        wbExceptionLogFileName = new Button(wMainConfigComp, SWT.PUSH | SWT.CENTER);
        props.setLook(wbExceptionLogFileName);
        wbExceptionLogFileName.setText(Messages.getString("browse"));
        fdbExceptionLogFileName = new FormData();
        fdbExceptionLogFileName.right = new FormAttachment(100, 0);
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
                        Messages.getString("*.txt"),
                        Messages.getString("所有文件")});
                if (dialog.open() != null) {
                    wExceptionLogFileName.setText(dialog.getFilterPath()
                            + System.getProperty("file.separator") + dialog.getFileName());
                }
            }
        });
        wMainConfigComp.layout();
        wMainConfigTab.setControl(wMainConfigComp);
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


    public String toString() {
        return this.getClass().getName();
    }
}
