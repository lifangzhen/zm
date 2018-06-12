/**
 * 提示消息
 * @param content 提示内容
 * @param time 自动关闭时间。0为不自动关闭
 * @param icon 图标样式
 * @param endFunc 关闭后回调函数
 */
function msg(content, time, icon, endFunc) {
    $.layer({
        title: false,
        time: time,
        dialog: {
            type: icon,
            msg: content
        },
        end: endFunc
    });
}