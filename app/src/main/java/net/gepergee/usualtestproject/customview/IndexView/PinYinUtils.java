package net.gepergee.usualtestproject.customview.IndexView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.Locale;

/**
 * @author petergee
 * @date 2018/8/29
 */
public class PinYinUtils {

    public static String getPinyin(String hanzi) {
        StringBuilder sb = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //由于不能直接对多个汉子转换，只能对单个汉子转换
        char[] arr = hanzi.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            if (Character.isWhitespace(arr[i])) {
                continue;
            }
            try {
                String[] pinyinArr = PinyinHelper.toHanyuPinyinStringArray(arr[i], format);
                if (pinyinArr != null) {
                    sb.append(pinyinArr[0]);
                } else {
                    sb.append(arr[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //不是正确的汉字
                sb.append(arr[i]);
            }

        }
        return sb.toString();
    }

 /**//**
     * 名字转拼音,取首字母
     *
     * @param name
     * @return
     *//*
    private String getSortLetter(String name) {
        String letter = "#";
        if (name == null) {
            return letter;
        }
        // 汉字转换成拼音
        // String pinyin = characterParser.getSelling(name);
        String pinyin = PinYin.getPinYin(name);
        Log.i("main", "pinyin:" + pinyin);
        String sortString = pinyin.substring(0, 1).toUpperCase(Locale.CHINESE);

        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }*/

    /**
     * 取sort_key的首字母
     *
     * @param sortKey
     * @return
     */
    private String getSortLetterBySortKey(String sortKey) {
        if (sortKey == null || "".equals(sortKey.trim())) {
            return null;
        }
        String letter = "#";
        // 汉字转换成拼音
        String sortString = sortKey.trim().substring(0, 1).toUpperCase(Locale.CHINESE);
        // 正则表达式，判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            letter = sortString.toUpperCase(Locale.CHINESE);
        }
        return letter;
    }


  /*  *//**
     * 加载联系人数据
     *//*
    private void loadContacts() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 插叙
                    String queryTye[] = { ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, "sort_key", "phonebook_label",
                            ContactsContract.CommonDataKinds.Phone.PHOTO_ID };
                    ContentResolver resolver = getApplicationContext().getContentResolver();
                    Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, queryTye, null, null,
                            "sort_key COLLATE LOCALIZED ASC");
                    if (phoneCursor == null || phoneCursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "未获得读取联系人权限 或 未获得联系人数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int PHONES_NUMBER_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    int PHONES_DISPLAY_NAME_INDEX = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                    int SORT_KEY_INDEX = phoneCursor.getColumnIndex("sort_key");
                    int PHONEBOOK_LABEL = phoneCursor.getColumnIndex("phonebook_label");
                    int PHOTO_ID = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_ID);
                    if (phoneCursor.getCount() > 0) {
                        mAllContactsList = new ArrayList<SortModel>();
                        while (phoneCursor.moveToNext()) {
                            String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                            if (TextUtils.isEmpty(phoneNumber))
                                continue;
                            // 头像id
                            long photoId = phoneCursor.getLong(PHOTO_ID);
                            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
                            String sortKey = phoneCursor.getString(SORT_KEY_INDEX);
                            String book = phoneCursor.getString(PHONEBOOK_LABEL);
                            SortModel sortModel = new SortModel(contactName, phoneNumber, sortKey);
                            // //优先使用系统sortkey取,取不到再使用工具取
                            // String sortLetters =
                            // getSortLetterBySortKey(sortKey);
                            // Log.i("main", "sortLetters:"+sortLetters);
                            // if (sortLetters == null) {
                            // sortLetters = getSortLetter(contactName);
                            // }
                            if (book == null) {
                                book = "#";
                            } else if (book.equals("#")) {
                                book = "#";
                            } else if (book.equals("")) {
                                book = "#";
                            }
                            sortModel.sortLetters = book;
                            sortModel.sortToken = parseSortKey(book);
                            mAllContactsList.add(sortModel);
                        }
                    }
                    phoneCursor.close();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Collections.sort(mAllContactsList, pinyinComparator);
                            adapter.updateListView(mAllContactsList);
                        }
                    });
                } catch (Exception e) {
                    Log.e("xbc", e.getLocalizedMessage());
                }
            }
        }).start();
    }
*/

  /*  *//**
     * 模糊查询
     *
     * @param str
     * @return
     *//*
    private List<SortModel> search(String str) {
        List<SortModel> filterList = new ArrayList<SortModel>();// 过滤后的list
        // if (str.matches("^([0-9]|[/+])*$")) {// 正则表达式 匹配号码
        if (str.matches("^([0-9]|[/+]).*")) {// 正则表达式
            // 匹配以数字或者加号开头的字符串(包括了带空格及-分割的号码)
            String simpleStr = str.replaceAll("\\-|\\s", "");
            for (SortModel contact : mAllContactsList) {
                if (contact.number != null && contact.name != null) {
                    if (contact.simpleNumber.contains(simpleStr) || contact.name.contains(str)) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        } else {
            for (SortModel contact : mAllContactsList) {
                if (contact.number != null && contact.name != null) {
                    // 姓名全匹配,姓名首字母简拼匹配,姓名全字母匹配
                    if (contact.name.toLowerCase(Locale.CHINESE).contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortKey.toLowerCase(Locale.CHINESE).replace(" ", "")
                            .contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortToken.simpleSpell.toLowerCase(Locale.CHINESE)
                            .contains(str.toLowerCase(Locale.CHINESE))
                            || contact.sortToken.wholeSpell.toLowerCase(Locale.CHINESE)
                            .contains(str.toLowerCase(Locale.CHINESE))) {
                        if (!filterList.contains(contact)) {
                            filterList.add(contact);
                        }
                    }
                }
            }
        }
        return filterList;
    }*/


}