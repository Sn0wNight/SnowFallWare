package net.ccbluex.liquidbounce.utils.render

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import java.awt.image.BufferedImage
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import javax.imageio.ImageIO

/**
 * @author DinoFeng
*/
@SideOnly(Side.CLIENT)
object IconUtils {
    val fileDir = "/assets/minecraft/fdpclient/misc/icon.png"
    val icon = readImageToBuffer(IconUtils::class.java.getResourceAsStream(fileDir))
    val icon32 = icon
    val icon16 = readImageToBuffer(ImageUtils.resizeImage(ImageIO.read(IconUtils::class.java.getResourceAsStream(fileDir)), 16, 16))
    val favicon: Array<ByteBuffer?>?
        get() {
            try {
                return arrayOf(
                    icon16,
                    icon32
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

    private fun readImageToBuffer(imageStream: InputStream?): ByteBuffer? {
        if (imageStream == null) return null
        val bufferedImage = ImageIO.read(imageStream)
        val rgb = bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, null, 0, bufferedImage.width)
        val byteBuffer = ByteBuffer.allocate(4 * rgb.size)
        for (i in rgb) byteBuffer.putInt(i shl 8 or (i shr 24 and 255))
        byteBuffer.flip()
        return byteBuffer
    }

    private fun readImageToBuffer(bufferedImage: BufferedImage?): ByteBuffer? {
        if (bufferedImage == null) return null
        val rgb = bufferedImage.getRGB(0, 0, bufferedImage.width, bufferedImage.height, null, 0, bufferedImage.width)
        val byteBuffer = ByteBuffer.allocate(4 * rgb.size)
        for (i in rgb) byteBuffer.putInt(i shl 8 or (i shr 24 and 255))
        byteBuffer.flip()
        return byteBuffer
    }
}
