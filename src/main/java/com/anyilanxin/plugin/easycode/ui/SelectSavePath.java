package com.anyilanxin.plugin.easycode.ui;

import com.anyilanxin.plugin.easycode.constants.StrState;
import com.anyilanxin.plugin.easycode.dict.GlobalDict;
import com.anyilanxin.plugin.easycode.dto.GenerateOptions;
import com.anyilanxin.plugin.easycode.dto.SettingsStorageDTO;
import com.anyilanxin.plugin.easycode.entity.GlobalConfigGroup;
import com.anyilanxin.plugin.easycode.entity.TableInfo;
import com.anyilanxin.plugin.easycode.entity.Template;
import com.anyilanxin.plugin.easycode.service.CodeGenerateService;
import com.anyilanxin.plugin.easycode.service.SettingsStorageService;
import com.anyilanxin.plugin.easycode.service.TableInfoSettingsService;
import com.anyilanxin.plugin.easycode.tool.CacheDataUtils;
import com.anyilanxin.plugin.easycode.tool.ModuleUtils;
import com.anyilanxin.plugin.easycode.tool.ProjectUtils;
import com.anyilanxin.plugin.easycode.tool.StringUtils;
import com.anyilanxin.plugin.easycode.ui.component.TemplateSelectComponent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.ExceptionUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;

/**
 * 选择保存路径
 *
 * @author makejava
 * @version 1.0.0
 * @since 2018/07/17 13:10
 */
public class SelectSavePath extends DialogWrapper {
    /**
     * 主面板
     */
    private JPanel contentPane;
    /**
     * 模型下拉框
     */
    private JComboBox<String> moduleComboBox;
    /**
     * 包字段
     */
    private JTextField packageField;
    /**
     * 路径字段
     */
    private JTextField pathField;
    /**
     * 前缀字段
     */
    private JTextField preField;
    /**
     * 版本号
     */
    private JTextField versionField;
    /**
     * 包选择按钮
     */
    private JButton packageChooseButton;
    /**
     * 路径选择按钮
     */
    private JButton pathChooseButton;
    /**
     * 模板面板
     */
    private JPanel templatePanel;
    /**
     * 统一配置复选框
     */
    private JCheckBox unifiedConfigCheckBox;
    /**
     * 弹框选是复选框
     */
    private JCheckBox titleSureCheckBox;
    /**
     * 格式化代码复选框
     */
    private JCheckBox reFormatCheckBox;
    /**
     * 弹框全否复选框
     */
    private JCheckBox titleRefuseCheckBox;
    /**
     * 作者
     */
    private JTextField authorField;
    /**
     * 全局配置
     */
    private JComboBox<String> selectGroupConfig;
    /**
     * 所有包名称映射
     */
    private final Map<String, String> haveNames;
    /**
     * 包名与名称反向映射
     */
    private final Map<String, String> haveNameRevert;
    /**
     * 数据缓存工具类
     */
    private final CacheDataUtils cacheDataUtils = CacheDataUtils.getInstance();
    /**
     * 表信息服务
     */
    private final TableInfoSettingsService tableInfoService;
    /**
     * 项目对象
     */
    private final Project project;
    /**
     * 代码生成服务
     */
    private final CodeGenerateService codeGenerateService;
    /**
     * 当前项目中的module
     */
    private final List<Module> moduleList;

    /**
     * 实体模式生成代码
     */
    private final boolean entityMode;

    /**
     * 模板选择组件
     */
    private TemplateSelectComponent templateSelectComponent;

    /**
     * 构造方法
     */
    public SelectSavePath(Project project) {
        this(project, false);
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return this.contentPane;
    }

    /**
     * 构造方法
     */
    public SelectSavePath(Project project, boolean entityMode) {
        super(project);
        this.entityMode = entityMode;
        this.project = project;
        this.haveNames = new HashMap<>(64);
        this.haveNameRevert = new HashMap<>(64);
        this.tableInfoService = TableInfoSettingsService.getInstance();
        this.codeGenerateService = CodeGenerateService.getInstance(project);
        boolean gradle = ProjectUtils.isGradle(project);
        // 初始化module，存在资源路径的排前面
        this.moduleList = new LinkedList<>();
        for (Module module : ModuleManager.getInstance(project).getModules()) {
            // 存在源代码文件夹放前面，否则放后面
            String name = module.getName();
            // 针对gradle项目过滤掉test
            if (gradle && (name.endsWith(".test") || name.endsWith(".main") || name.endsWith(".build"))) {
                continue;
            }
            if (ModuleUtils.existsSourcePath(module)) {
                this.moduleList.add(0, module);
            } else {
                this.moduleList.add(module);
            }
        }
        this.moduleList.sort(Comparator.comparing(Module::getName));
        this.initPanel(gradle);
        this.refreshData();
        this.initEvent();
        init();
        setTitle(GlobalDict.TITLE_INFO);
        //初始化路径
        refreshPath();
    }

    private void initEvent() {
        //监听module选择事件
        moduleComboBox.addActionListener(e -> {
            // 刷新路径
            refreshPath();
        });

        try {
            Class<?> cls = Class.forName("com.intellij.ide.util.PackageChooserDialog");
            //添加包选择事件
            packageChooseButton.addActionListener(e -> {
                try {
                    Constructor<?> constructor = cls.getConstructor(String.class, Project.class);
                    Object dialog = constructor.newInstance("Package Chooser", project);
                    // 显示窗口
                    Method showMethod = cls.getMethod("show");
                    showMethod.invoke(dialog);
                    // 获取选中的包名
                    Method getSelectedPackageMethod = cls.getMethod("getSelectedPackage");
                    Object psiPackage = getSelectedPackageMethod.invoke(dialog);
                    if (psiPackage != null) {
                        Method getQualifiedNameMethod = psiPackage.getClass().getMethod("getQualifiedName");
                        String packageName = (String) getQualifiedNameMethod.invoke(psiPackage);
                        packageField.setText(packageName);
                        // 刷新路径
                        refreshPath();
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                         InvocationTargetException e1) {
                    ExceptionUtil.rethrow(e1);
                }
            });

            // 添加包编辑框失去焦点事件
            packageField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    // 刷新路径
                    refreshPath();
                }
            });
        } catch (ClassNotFoundException e) {
            // 没有PackageChooserDialog，并非支持Java的IDE，禁用相关UI组件
            packageField.setEnabled(false);
            packageChooseButton.setEnabled(false);
        }

        //选择路径
        pathChooseButton.addActionListener(e -> {
            //将当前选中的model设置为基础路径
            VirtualFile path = ProjectUtils.getBaseDir(project);
            Module module = getSelectModule();
            if (module != null) {
                path = ModuleUtils.getSourcePath(module);
            }
            VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, path);
            if (virtualFile != null) {
                pathField.setText(virtualFile.getPath());
            }
        });
    }

    private void refreshData() {
        // 获取选中的表信息（鼠标右键的那张表），并提示未知类型
        TableInfo tableInfo;
        if (entityMode) {
            tableInfo = tableInfoService.getTableInfo(cacheDataUtils.getSelectPsiClass());
        } else {
            tableInfo = tableInfoService.getTableInfo(cacheDataUtils.getSelectDbTable());
        }

        // 设置默认配置信息
        if (!StringUtils.isEmpty(tableInfo.getSaveModelName())) {
            moduleComboBox.setSelectedItem(haveNameRevert.get(tableInfo.getSaveModelName()));
        }
        if (!StringUtils.isEmpty(tableInfo.getSavePackageName())) {
            packageField.setText(tableInfo.getSavePackageName());
        }
        if (!StringUtils.isEmpty(tableInfo.getPreName())) {
            preField.setText(tableInfo.getPreName());
        }
        if (!StringUtils.isEmpty(tableInfo.getVersion())) {
            versionField.setText(tableInfo.getVersion());
        }
        SettingsStorageDTO settings = SettingsStorageService.getSettingsStorage();
        String groupName = settings.getCurrTemplateGroupName();
        if (!StringUtils.isEmpty(tableInfo.getTemplateGroupName())) {
            if (settings.getTemplateGroupMap().containsKey(tableInfo.getTemplateGroupName())) {
                groupName = tableInfo.getTemplateGroupName();
            }
        }
        templateSelectComponent.setSelectedGroupName(groupName);

        String groupConfig = settings.getCurrGlobalConfigGroupName();
        if (!StringUtils.isEmpty(tableInfo.getGlobalConfigGroupName())) {
            if (settings.getGlobalConfigGroupMap().containsKey(tableInfo.getGlobalConfigGroupName())) {
                groupConfig = tableInfo.getGlobalConfigGroupName();
            }
        }
        this.selectGroupConfig.setSelectedItem(groupConfig);

        String savePath = tableInfo.getSavePath();
        if (!StringUtils.isEmpty(savePath)) {
            // 判断是否需要拼接项目路径
            if (savePath.startsWith(StrState.RELATIVE_PATH)) {
                String projectPath = project.getBasePath();
                savePath = projectPath + savePath.substring(1);
            }
            pathField.setText(savePath);
        }
        // 初始化作者
        if (!StringUtils.isEmpty(tableInfo.getAuthor())) {
            authorField.setText(tableInfo.getAuthor());
        } else {
            if (!StringUtils.isEmpty(settings.getAuthor())) {
                authorField.setText(settings.getAuthor());
            }
        }
    }

    @Override
    protected void doOKAction() {
        onOK();
        super.doOKAction();
    }


    public GlobalConfigGroup getSelectGroupConfig() {
        String groupName = (String) this.selectGroupConfig.getSelectedItem();
        if (StringUtils.isEmpty(groupName)) {
            return null;
        }
        return SettingsStorageService.getSettingsStorage().getGlobalConfigGroupMap().get(groupName);
    }

    /**
     * 确认按钮回调事件
     */
    private void onOK() {
        List<Template> selectTemplateList = templateSelectComponent.getAllSelectedTemplate();
        // 如果选择的模板是空的
        if (selectTemplateList.isEmpty()) {
            Messages.showWarningDialog("Can't Select Template!", GlobalDict.TITLE_INFO);
            return;
        }
        GlobalConfigGroup selectedGroup = this.getSelectGroupConfig();
        if (Objects.isNull(selectedGroup)) {
            Messages.showWarningDialog("Can't Select Global Config!", GlobalDict.TITLE_INFO);
            return;
        }
        String savePath = pathField.getText();
        if (StringUtils.isEmpty(savePath)) {
            Messages.showWarningDialog("Can't Select Save Path!", GlobalDict.TITLE_INFO);
            return;
        }
        // 针对Linux系统路径做处理
        savePath = savePath.replace("\\", "/");
        // 保存路径使用相对路径
        String basePath = project.getBasePath();
        if (!StringUtils.isEmpty(basePath) && savePath.startsWith(basePath)) {
            if (savePath.length() > basePath.length()) {
                if ("/".equals(savePath.substring(basePath.length(), basePath.length() + 1))) {
                    savePath = savePath.replace(basePath, ".");
                }
            } else {
                savePath = savePath.replace(basePath, ".");
            }
        }
        // 保存配置
        TableInfo tableInfo;
        if (!entityMode) {
            tableInfo = tableInfoService.getTableInfo(cacheDataUtils.getSelectDbTable());
        } else {
            tableInfo = tableInfoService.getTableInfo(cacheDataUtils.getSelectPsiClass());
        }
        tableInfo.setSavePath(savePath);
        tableInfo.setSavePackageName(packageField.getText());
        tableInfo.setPreName(preField.getText());
        tableInfo.setAuthor(authorField.getText());
        tableInfo.setVersion(versionField.getText());
        tableInfo.setTemplateGroupName(templateSelectComponent.getSelectedGroupName());
        tableInfo.setGlobalConfigGroupName((String) this.selectGroupConfig.getSelectedItem());
        Module module = getSelectModule();
        if (module != null) {
            tableInfo.setSaveModelName(module.getName());
        }
        // 保存配置
        tableInfoService.saveTableInfo(tableInfo);

        // 生成代码
        codeGenerateService.generate(selectTemplateList, getGenerateOptions(), selectedGroup);
    }

    /**
     * 初始化方法
     */
    private void initPanel(boolean gradle) {
        // 初始化模板组
        this.templateSelectComponent = new TemplateSelectComponent();
        templatePanel.add(this.templateSelectComponent.getMainPanel(), BorderLayout.CENTER);
        //初始化Module选择
        for (Module module : this.moduleList) {
            String name = module.getName();
            if (gradle) {
                String[] split = name.split("\\.");
                String shotName = split[split.length - 1];
                if (!haveNames.containsKey(shotName)) {
                    name = shotName;
                }
            }
            haveNames.put(name, module.getName());
            haveNameRevert.put(module.getName(), name);
            moduleComboBox.addItem(name);
        }
        // 初始化全局配置
        this.selectGroupConfig.removeAllItems();
        for (String groupName : SettingsStorageService.getSettingsStorage().getGlobalConfigGroupMap().keySet()) {
            this.selectGroupConfig.addItem(groupName);
        }
    }


    /**
     * 获取生成选项
     *
     * @return {@link GenerateOptions}
     */
    private GenerateOptions getGenerateOptions() {
        return GenerateOptions.builder()
                .entityModel(this.entityMode)
                .reFormat(reFormatCheckBox.isSelected())
                .titleSure(titleSureCheckBox.isSelected())
                .titleRefuse(titleRefuseCheckBox.isSelected())
                .unifiedConfig(unifiedConfigCheckBox.isSelected())
                .build();
    }

    /**
     * 获取选中的Module
     *
     * @return 选中的Module
     */
    private Module getSelectModule() {
        String name = (String) moduleComboBox.getSelectedItem();
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String moduleName = haveNames.get(name);
        return ModuleManager.getInstance(project).findModuleByName(moduleName);
    }

    /**
     * 获取基本路径
     *
     * @return 基本路径
     */
    private String getBasePath() {
        Module module = getSelectModule();
        VirtualFile baseVirtualFile = ProjectUtils.getBaseDir(project);
        if (baseVirtualFile == null) {
            Messages.showWarningDialog("无法获取到项目基本路径！", GlobalDict.TITLE_INFO);
            return "";
        }
        String baseDir = baseVirtualFile.getPath();
        if (module != null) {
            VirtualFile virtualFile = ModuleUtils.getSourcePath(module);
            if (virtualFile != null) {
                baseDir = virtualFile.getPath();
            }
        }
        return baseDir;
    }

    /**
     * 刷新目录
     */
    private void refreshPath() {
        String packageName = packageField.getText();
        // 获取基本路径
        String path = getBasePath();
        // 兼容Linux路径
        path = path.replace("\\", "/");
        // 如果存在包路径，添加包路径
        if (!StringUtils.isEmpty(packageName)) {
            path += "/" + packageName.replace(".", "/");
        }
        pathField.setText(path);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
