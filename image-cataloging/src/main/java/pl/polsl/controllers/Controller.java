package pl.polsl.controllers;

import java.awt.CardLayout;

import pl.polsl.views.*;

public class Controller {

    private final MainWindow view;
    private final TableViewController tableViewController;
    private final FolderViewController folderViewController;

    public Controller(MainWindow v) {
        view = v;
        folderViewController = new FolderViewController(view.getFolderView(),view);
        tableViewController = new TableViewController(view.getTableView(), folderViewController);
        initView();
        view.draw(view);

    }

    public void initView() {
        tableViewController.fillTable();
    }

    public void initController() {
        view.getPreviousButton().addActionListener(e -> previousPageClicked());
        view.getNextButton().addActionListener(e -> nextPageClicked());

    }

    private void previousPageClicked() {
        view.getPreviousButton().setEnabled(false);
        view.getNextButton().setEnabled(true);
        CardLayout cardLayout = (CardLayout) (view.getCardsContainer().getLayout());
        cardLayout.previous(view.getCardsContainer());
    }

    private void nextPageClicked() {
        view.getPreviousButton().setEnabled(true);
        view.getNextButton().setEnabled(false);
        CardLayout cardLayout = (CardLayout) (view.getCardsContainer().getLayout());
        cardLayout.next(view.getCardsContainer());
    }
}
