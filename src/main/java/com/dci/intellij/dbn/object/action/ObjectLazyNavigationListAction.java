package com.dci.intellij.dbn.object.action;

import com.dci.intellij.dbn.object.common.DBObject;
import com.dci.intellij.dbn.object.common.list.DBObjectNavigationList;
import com.dci.intellij.dbn.object.lookup.DBObjectRef;
import com.intellij.openapi.actionSystem.AnAction;

import java.util.List;

public class ObjectLazyNavigationListAction extends ObjectListShowAction {
    private DBObjectNavigationList navigationList;
    private DBObjectRef<DBObject> parentObjectRef;

    public ObjectLazyNavigationListAction(DBObject parentObject, DBObjectNavigationList navigationList) {
        super(navigationList.getName() + "...", parentObject);
        this.navigationList = navigationList;
        this.parentObjectRef = DBObjectRef.of(parentObject);
    }

    @Override
    public List<? extends DBObject> getObjectList() {
        List<DBObject> objects = navigationList.getObjects();
        if (objects == null) objects = navigationList.getObjectsProvider().getObjects();
        return objects;
    }

    @Override
    public String getTitle() {
        return navigationList.getName();
    }

    @Override
    public String getEmptyListMessage() {
        return "No " + navigationList.getName() + " found";
    }

    @Override
    public String getListName() {
        return navigationList.getName();
    }

/*    @Override
    public void actionPerformed(final AnActionEvent e) {
        new BackgroundTask(parentObject.getProject(), "Loading " + navigationList.getName(), false, true) {
            @Override
            public void execute(@NotNull ProgressIndicator progressIndicator) {
                final ObjectNavigationListActionGroup linksActionGroup =
                        new ObjectNavigationListActionGroup(parentObject, navigationList, true);

                new SimpleLaterInvocator() {
                    public void run() {
                        ListPopup popup = JBPopupFactory.getInstance().createActionGroupPopup(
                                navigationList.getName(),
                                linksActionGroup,
                                e.getDataContext(),
                                JBPopupFactory.ActionSelectionAid.SPEEDSEARCH,
                                true, null, 10);

                        Project project = ActionUtil.getProject();
                        popup.showInCenterOf(DatabaseBrowserManager.getInstance(project).getBrowserPanel().getTree());
                    }
                }.start();
            }
        }.start();
    }*/

    @Override
    protected AnAction createObjectAction(DBObject object) {
        DBObject sourceObject = DBObjectRef.ensure(parentObjectRef);
        return new NavigateToObjectAction(sourceObject, object);
    }
}
