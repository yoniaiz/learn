package ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserResponseModel {
    private RequestOperation requestOperation;
    private ResponseStatus responseStatus;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
