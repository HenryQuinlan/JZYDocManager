package team.JZY.DocManager.util;

import team.JZY.DocManager.DocManagerApplication;
import team.JZY.DocManager.data.RecordRepository;
import team.JZY.DocManager.model.Record;

public class RecordOperateUtil {

    public static void download(DocManagerApplication.Activity activity,Record record) {

        RecordRepository recordRepository = RecordRepository.getInstance(activity);
        FileOpenUtil.downloadAndView(activity,ConvertUtil.RecordConvertToDocInfo(record));
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_DOWNLOAD,
                record.getDocID(),
                record.getDocName(),
                record.getDocType());
    }
    public static void visit(DocManagerApplication.Activity activity,Record record) {

        RecordRepository recordRepository = RecordRepository.getInstance(activity);
        FileOpenUtil.preview(activity,ConvertUtil.RecordConvertToDocInfo(record));
        recordRepository.insertRecord(
                activity.getLoggedInUserName(),
                Record.TYPE_VISIT,
                record.getDocID(),
                record.getDocName(),
                record.getDocType());
    }

    public static void favorite(DocManagerApplication.Activity activity,Record record, boolean isFavorite) {

        RecordRepository recordRepository = RecordRepository.getInstance(activity);
        if(isFavorite) {
            recordRepository.deleteRecord(new Record(
                    activity.getLoggedInUserName(),
                    Record.TYPE_FAVORITE,
                    record.getDocID(),
                    record.getDocName(),
                    record.getDocType()
            ));
        }
        else {
            recordRepository.insertRecord(
                    activity.getLoggedInUserName(),
                    Record.TYPE_FAVORITE,
                    record.getDocID(),
                    record.getDocName(),
                    record.getDocType());
        }
    }
}
