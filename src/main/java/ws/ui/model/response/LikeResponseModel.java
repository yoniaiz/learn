package ws.ui.model.response;

public class LikeResponseModel {
    LikeEum status;
    long themeId;

    public LikeEum getStatus() {
        return status;
    }

    public void setStatus(LikeEum status) {
        this.status = status;
    }

    public long getThemeId() {
        return themeId;
    }

    public void setThemeId(long themeId) {
        this.themeId = themeId;
    }
}
