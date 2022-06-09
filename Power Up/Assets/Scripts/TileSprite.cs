using System;
using UnityEngine;

[Serializable]
public class TileSprite
{
    public string Name;
    public GameObject TilePrefab;
    public Tiles TileType;

    public TileSprite()
    {
        Name = "Unset";
        TilePrefab = null;
        TileType = Tiles.Unset;
    }

    public TileSprite(string name, GameObject prefab, Tiles tile)
    {
        Name = name;
        TilePrefab = prefab;
        TileType = tile;
    }
}