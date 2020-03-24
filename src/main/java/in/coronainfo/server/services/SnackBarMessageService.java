package in.coronainfo.server.services;

import in.coronainfo.server.model.SnackBarMessage;
import in.coronainfo.server.repository.SnackBarMessageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
public class SnackBarMessageService {

    @NonNull
    private SnackBarMessageRepository snackBarMessageRepository;

    public SnackBarMessage getById(String id) {
        return snackBarMessageRepository.getById(id);
    }

    public Map<String, SnackBarMessage> getByIds(String... ids) {
        return snackBarMessageRepository.getByIds(ids);
    }

    public boolean addSnackBarMessage(SnackBarMessage snackBarMessage) {
        snackBarMessageRepository.addSnackBarMessage(snackBarMessage);
        return true;
    }

    public List<SnackBarMessage> getAllSnackBarMessages() {
        List<SnackBarMessage> snackBarMessageList = snackBarMessageRepository.getAllSnackBarMessages();
        return snackBarMessageList;
    }
}