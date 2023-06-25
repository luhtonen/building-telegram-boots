import std.concurrency : spawn, thisTid;
import std.stdio : writeln;
import core.atomic : atomicOp;

shared int flag = 0;

void worker()
{
    atomicOp!"+="(flag, 1);
    writeln("Thread ", thisTid, ":global>", flag);
}

// to run execute following command:
// dmd -run threads/threads3.d
void main()
{
    int TOTAL_WORKERS = 20;
    for (int i = 0; i < TOTAL_WORKERS; ++i) {
        spawn(&worker);
    }
}
