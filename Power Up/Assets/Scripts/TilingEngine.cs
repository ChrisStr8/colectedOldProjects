using UnityEngine;
using System.Collections;
using System.Collections.Generic;
using Lean;

public class TilingEngine : MonoBehaviour
{
    public List<TileSprite> TileSprites;
    public Vector2 MapSize;
    public GameObject TileContainerPrefab;
    public GameObject DefaultPrefab;

    private TileSprite[,] _map;
    private GameObject controller;
    private GameObject _tileContainer;
    private List<GameObject> _tiles = new List<GameObject>();

    public void Start()
    {
        controller = GameObject.Find("Controller");
        _map = new TileSprite[(int)MapSize.x, (int)MapSize.y];

        DefaultTiles();
        SetTiles();
    }

    private void DefaultTiles()
    {
        for (var y = 0; y < MapSize.y - 1; y++)
        {
            for (var x = 0; x < MapSize.x - 1; x++)
            {
                _map[x, y] = new TileSprite("unset", DefaultPrefab, Tiles.Unset);
            }
        }
    }

    private void SetTiles()
    {
        for (var y = 0; y < MapSize.y; y++)
        {
            for (var x = 0; x < MapSize.x; x++)
            {
                if(x == 0 || y == 0 || x == MapSize.x - 1 || y == MapSize.y - 1) {
                    _map[x, y] = new TileSprite(TileSprites[1].Name, TileSprites[1].TilePrefab, TileSprites[1].TileType);
                } else {
                    _map[x, y] = new TileSprite(TileSprites[0].Name, TileSprites[0].TilePrefab, TileSprites[0].TileType);
                }
            }
        }
    }

    private void Update()
    {
        AddTilesToWorld();
    }

    private void AddTilesToWorld()
    {
        foreach (GameObject o in _tiles)
        {
            LeanPool.Despawn(o);
        }
        _tiles.Clear();
        LeanPool.Despawn(_tileContainer);
        _tileContainer = LeanPool.Spawn(TileContainerPrefab);
        var tileSize = .64f;
        //var viewOffsetX = ViewPortSize.x / 2f;
        //var viewOffsetY = ViewPortSize.y / 2f;
        for (var y = 0; y < MapSize.y; y++)
        {
            for (var x = 0; x < MapSize.x; x++)
            {
                var tX = (x * tileSize) - 5;
                var tY = (y * tileSize) - 4;

                //var iX = x + CurrentPosition.x;
                //var iY = y + CurrentPosition.y;

                /*if (iX < 0) continue;
                if (iY < 0) continue;
                if (iX > MapSize.x - 2) continue;
                if (iY > MapSize.y - 2) continue;*/

                var t = LeanPool.Spawn(_map[(int)x, (int)y].TilePrefab);
                t.transform.position = new Vector3(tX, tY, 0);
                t.transform.SetParent(_tileContainer.transform);
                _tiles.Add(t);
            }
        }
    }

    private TileSprite FindTile(Tiles tile)
    {
        foreach (TileSprite tileSprite in TileSprites)
        {
            if (tileSprite.TileType == tile) return tileSprite;
        }
        return null;
    }
}