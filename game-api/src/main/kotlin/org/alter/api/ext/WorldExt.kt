package org.alter.api.ext

import org.alter.game.model.World
import org.alter.game.model.collision.WALL_DIAGONAL
import org.alter.game.model.entity.DynamicObject
import org.alter.game.model.entity.GameObject
import org.alter.rscm.RSCM.getRSCM
import kotlin.math.abs

fun World.openDoor(
    obj: GameObject,
    opened: Int = obj.id + 1,
    invertRot: Boolean = false,
    invertTransform: Boolean = false,
): GameObject {
    val oldRot = obj.rot
    val newRot = abs((oldRot + (if (invertRot) -1 else 1)) and 0x3)
    val diagonal = obj.type == WALL_DIAGONAL

    val newTile =
        when (oldRot) {
            0 -> if (diagonal) obj.tile.transform(0, 1) else obj.tile.transform(if (invertTransform) 1 else -1, 0)
            1 -> if (diagonal) obj.tile.transform(1, 0) else obj.tile.transform(0, if (invertTransform) -1 else 1)
            2 -> if (diagonal) obj.tile.transform(0, -1) else obj.tile.transform(if (invertTransform) -1 else 1, 0)
            3 -> if (diagonal) obj.tile.transform(-1, 0) else obj.tile.transform(0, if (invertTransform) 1 else -1)
            else -> throw IllegalStateException("Invalid door rotation: [currentRot=$oldRot, replaceRot=$newRot]")
        }

    val newDoor = DynamicObject(id = opened, type = obj.type, rot = newRot, tile = newTile)
    remove(obj)
    spawn(newDoor)
    return newDoor
}

/**
 * Opens [obj] using the object name of its opened state.
 *
 * @param obj door object currently placed in the world
 * @param opened RSCM identifier for the opened door variant
 * @param invertRot when `true`, rotates the new door in the opposite direction
 * @param invertTransform when `true`, mirrors the tile offset of the opened door
 * @return the spawned opened door
 */
fun World.openDoor(
    obj: GameObject,
    opened: String,
    invertRot: Boolean = false,
    invertTransform: Boolean = false,
): GameObject = this.openDoor(obj, getRSCM(opened), invertRot, invertTransform)


fun World.closeDoor(
    obj: GameObject,
    closed: Int = obj.id - 1,
    invertRot: Boolean = false,
    invertTransform: Boolean = false,
): GameObject {
    val oldRot = obj.rot
    val newRot = abs((oldRot + (if (invertRot) 1 else -1)) and 0x3)
    val diagonal = obj.type == WALL_DIAGONAL

    val newTile =
        when (oldRot) {
            0 -> if (diagonal) obj.tile.transform(1, 0) else obj.tile.transform(0, if (invertTransform) -1 else 1)
            1 -> if (diagonal) obj.tile.transform(0, -1) else obj.tile.transform(if (invertTransform) -1 else 1, 0)
            2 -> if (diagonal) obj.tile.transform(-1, 0) else obj.tile.transform(0, if (invertTransform) 1 else -1)
            3 -> if (diagonal) obj.tile.transform(0, 1) else obj.tile.transform(if (invertTransform) 1 else -1, 0)
            else -> throw IllegalStateException("Invalid door rotation: [currentRot=$oldRot, replaceRot=$newRot]")
        }

    val newDoor = DynamicObject(id = closed, type = obj.type, rot = newRot, tile = newTile)
    remove(obj)
    spawn(newDoor)
    return newDoor
}

/**
 * Closes [obj] using the object name of its closed state.
 *
 * @param obj door object currently placed in the world
 * @param closed RSCM identifier for the closed door variant
 * @param invertRot when `true`, rotates the new door in the opposite direction
 * @param invertTransform when `true`, mirrors the tile offset of the closed door
 * @return the spawned closed door
 */
fun World.closeDoor(
    obj: GameObject,
    closed: String,
    invertRot: Boolean = false,
    invertTransform: Boolean = false,
): GameObject = this.closeDoor(obj, getRSCM(closed), invertRot, invertTransform)

//
// fun World.getSettings() : Settings {
//    return this.settings as Settings
// }
