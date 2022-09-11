abstract class Handler {
    protected Handler successor;//继任者:处理不了的话把哀求传递给继任者

    public void setSuccessor(Handler successsor) {
        this.successor = successsor;
    }

    public abstract void HandleRequest(int request);//处理请求
}
