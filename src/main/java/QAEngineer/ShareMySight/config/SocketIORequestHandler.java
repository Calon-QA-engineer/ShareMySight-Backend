package QAEngineer.ShareMySight.config;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

public class SocketIORequestHandler extends ChannelInboundHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    if (msg instanceof FullHttpRequest) {
      FullHttpRequest req = (FullHttpRequest) msg;
      String uri = req.uri();
      
      if (uri.equals("/socket.io/")) {
        // Jika permintaan adalah ke /socket.io/, berikan respon yang sesuai
        String responseContent = "Socket.IO is running";
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(
          HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
          Unpooled.wrappedBuffer(responseContent.getBytes())
        );
        ctx.writeAndFlush(response);
      } else {
        // Lakukan hal lain untuk permintaan yang lain jika perlu
        ctx.fireChannelRead(msg);
      }
    }
  }
}
